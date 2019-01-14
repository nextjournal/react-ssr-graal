
(require '[cljs.build.api :as cljs])

(cljs/build
 "src"
 {:main 'foo.core
  :output-to "out/main.js"
  :verbose true
  :optimizations :whitespace})
