(ns renataly.android.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync reg-event-db reg-sub]]
            [renataly.events]
            [renataly.subs]))

(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))

(def logo-img (js/require "./images/cljs.png"))

(defn alert [title]
  (.alert (.-Alert ReactNative) title))

(reg-event-db
  :toggle
  (fn [db _]
    (assoc db :show-text (not (:show-text db)))))

(reg-sub
  :show-text
  (fn [db _]
    (:show-text db)))

(defonce do-timer (-> #(let [now (js/Date.)]
                         (dispatch [:toggle]))
                      (js/setInterval 1000)))

(defn blink [t]
  (let [show-text (subscribe [:show-text])]
    (fn []
      [text (if @show-text t "")])))


(defn app-root []
  [view
   [blink "I love to blink"]
   [blink "Yes blinking is so great"]
   [blink "Why did they ever take this out of HTML"]
   [blink "Look at me look at me look at me"]])

(defn init []
  (dispatch-sync [:initialize-db])
  (.registerComponent app-registry "renataly" #(r/reactify-component app-root)))
