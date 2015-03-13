(ns yournukomeetroleat.templates
  (:require [net.cgrand.enlive-html :refer [deftemplate content]]))

(deftemplate tpl-index "public/index.html"
  [value]
  [:#message] (content value))

(deftemplate tpl-roles "public/roles.html"
  [value]
  [:#message] (content value))
