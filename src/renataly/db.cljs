(ns renataly.db
  (:require [clojure.spec :as s]))

;; spec of app-db
(s/def ::show-text boolean?)
(s/def ::app-db
  (s/keys :req-un [::show-text]))

;; initial state of app-db
(def app-db {:show-text true})
