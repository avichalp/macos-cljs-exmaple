(ns example.app
  (:require
   [cljs.reader :as rdr]
   [shadow.react-native :refer (render-root)]
   ["react-native" :as rn]
   [reagent.core :as r]
   [reagent.dom :as rd]
   [reagent.ratom :as ra]
   [datascript.core :as d]
   [posh.reagent :refer [pull q posh!]]))




(def conn (d/create-conn {}))

;; An exmaple: store a string to later query in the component
(d/transact! conn [{:db/id -1
                    :app/welcome-message  "Hello from datascript"}])


;; Start Posh
(posh! conn)


(defn root []
  (let [[greeting] @(q '[:find [?a]
                         :where
                         [_ :app/welcome-message ?a]]
                       conn)]
      [:> rn/View
       {:style #js {:flex         1
                    :marginLeft   256
                    :marginRight  256
                    :marginTop    128
                    :marginBottom 128}}
       [:> rn/Text {} greeting]]))



(defn start
  {:dev/after-load true}
  [& args]
  ;; Read the data from disk, before starting the app
  (render-root "app" (r/as-element [root])))


(defn main []
  (start))
