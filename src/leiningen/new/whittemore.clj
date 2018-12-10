(ns leiningen.new.whittemore
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "whittemore"))

(defn whittemore
  "Create a new Whittemore project"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' whittemore project.")
    (->files data
             ["src/{{sanitized}}/core.clj" (render "core.clj" data)])))
