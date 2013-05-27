(ns yournukomeetroleat.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [clojure.math.combinatorics :as combo]
            [ring.middleware.json :as middleware]
            [clj-time.core :as dt]
            [clj-time.format :as f]))

(def week-formatter (f/formatter "ww"))
(def custom-formatter (f/formatter "YYMMdd"))

(def role-pairs (combo/combinations [:salesman :accountant :communication :sysadmin :networker :farfadet] 2))
(def members [:zaiste :tomkuk :tukan :yaroq :alf :adrien])

(defn week [datetime]
  (read-string (f/unparse week-formatter datetime)))

(defn datetime [date-str]
  (f/parse custom-formatter date-str))

(defn per-date [datetime member]
  (let [w (week datetime)]
    (let [result (zipmap (nth (combo/permutations members) w) role-pairs)]
      (if member result result))))

(defroutes app-routes
  (GET "/" [] "Your Role at Nukomeet")
  (GET "/for/now" [] (str (per-date (dt/now))))
  (GET "/for/:date" {params :params} (str (per-date (datetime (params :date)) (params :member))))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)))
