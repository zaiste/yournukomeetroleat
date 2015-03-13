(ns yournukomeetroleat.handler
  (:use compojure.core)
  (:use [ring.adapter.jetty :only [run-jetty]])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [clojure.math.combinatorics :as combo]
            [ring.middleware.json :as middleware]
            [clj-time.core :as dt]
            [clj-time.format :as f]
            [yournukomeetroleat.templates :refer [tpl-index tpl-roles]]))

(def week-formatter (f/formatter "ww"))
(def custom-formatter (f/formatter "YYMMdd"))

(def members [:zaiste :tomkuk :tukan :yaroq :alf :adrien])
(def roles [:salesman :accountant :communication :sysadmin :networker :farfadet])

(defn rotate [n coll]
  (let [ntime (if (neg? n) (- n) n)
        lshift #(concat (rest %) [(first %)])
        rshift #(cons (last %) (drop-last %))]
    ((apply comp (repeat ntime (if (neg? n) rshift lshift))) coll)))

(defn week [datetime]
  (read-string (f/unparse week-formatter datetime)))

(defn datetime [date-str]
  (f/parse custom-formatter date-str))

(defn per-date [datetime & member]
  (let [w (week datetime)]
    (let [result (zipmap (nth (combo/permutations members) w) (zipmap roles (rotate w roles)))]
      (if member result result))))

(defroutes app-routes
  (GET "/" [] (tpl-index "Index"))
  (GET "/for/now" [] (tpl-roles "Roles")) ;(str (per-date (dt/now))))
  (GET "/for/:date" {params :params} (str (per-date (datetime (params :date)) (params :member))))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

(defn -main [port]
  (run-jetty app {:port (Integer. port)}))
