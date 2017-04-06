(ns renataly.android.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
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

(defn greeting [props]
  (let [greeting-text (subscribe [:get-greeting])]
    (fn []
      [text {:style {:font-size 30 :font-weight "100" :margin-bottom 20 :text-align "center"}}
       (str @greeting-text (:name props))])))

(defn app-root []
  [view {:style {:flex-direction "column" :margin 40 :align-items "center"}}
   [greeting {:name "Cog"}]
   [greeting {:name "Juliella"}]
   [greeting {:name "Jeaye"}]
   [greeting {:name "Penny"}]
   [image {:source logo-img
           :style  {:width 80 :height 80 :margin-bottom 30}}]
   [touchable-highlight {:style {:background-color "#999" :padding 10 :border-radius 5}
                         :on-press #(alert "HELLO!")}
    [text {:style {:color "white" :text-align "center" :font-weight "bold"}} "press me"]]])

(defn init []
      (dispatch-sync [:initialize-db])
      (.registerComponent app-registry "renataly" #(r/reactify-component app-root)))
