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

(def bigblue {:color "blue"
             :font-weight "bold"
             :font-size 30
             })

(def red {:color "red"})

(defn app-root []
  [view
   [text {:style red} "just red"]
   [text {:style bigblue} "just bigblue"]
   [text {:style (merge bigblue red)} "bigblue then red"]
   [text {:style (merge red bigblue)} "red then bigblue"]])

(defn init []
  (dispatch-sync [:initialize-db])
  (.registerComponent app-registry "renataly" #(r/reactify-component app-root)))
