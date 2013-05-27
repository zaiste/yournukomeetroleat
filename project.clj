(defproject yournukomeetroleat "0.1.0-SNAPSHOT"
  :description "Show a Nukomeet role for given week"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/math.combinatorics "0.0.4"]
                 [compojure "1.1.5"]
                 [ring/ring-jetty-adapter "1.1.0"]
                 [ring/ring-json "0.2.0"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler yournukomeetroleat.handler/app}
  :profiles
  :min-lein-version "2.0.0"
  {:dev {:dependencies [[ring-mock "0.1.5"]]}})
