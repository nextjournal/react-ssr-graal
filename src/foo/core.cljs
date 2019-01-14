(ns foo.core
  (:require [reagent.core :as reagent]
            [reagent.dom.server :as dom-server]
            [cognitect.transit :as transit]))


(defn child [name]
  [:p "Hi, I am " name])

(defn childcaller [article]
  [child (:title article)])

(def transit-reader (transit/reader :json))

(defn render [article]
  (let [a (transit/read transit-reader article)]
    (dom-server/render-to-string [childcaller a])))
