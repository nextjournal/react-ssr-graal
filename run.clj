(import '[org.graalvm.polyglot Context Source])
(require '[clojure.java.io :as io])
(require '[cognitect.transit :as transit])
(import [java.io ByteArrayOutputStream])

(defn transit-encode
  ([x] (transit-encode x :json-verbose))
  ([x writer-kind]
   (let [baos (ByteArrayOutputStream.)
         w    (transit/writer baos writer-kind)
         _    (transit/write w x)
         ret  (.toString baos "utf-8")]
     (.reset baos)
     ret)))

(let [article    (transit-encode {:title "my title"})
      polyglot   (Context/create (into-array String ["js"]))
      jsBindings (doto (.getBindings polyglot "js")
                   (.putMember "article" article))
      src        (.build (Source/newBuilder "js" (io/file "out/main.js")))]
  (.eval polyglot src)
  (println (.. polyglot
               (eval "js" "foo.core.render(article)")
               asString)))
