(ns leiningen.new.whittemore
  (:require [leiningen.new.templates :refer [renderer year date project-name
                                             ->files sanitize-ns name-to-path
                                             multi-segment raw-resourcer]]
            [leiningen.core.main :as main]))

(defn whittemore
  "Create a new Whittemore project"
  [name]
  (let [render (renderer "whittemore")
        raw (raw-resourcer "whittemore")
        main-ns (multi-segment (sanitize-ns name))
        data {:raw-name name
              :name (project-name name)
              :namespace main-ns
              :nested-dirs (name-to-path main-ns)
              :year (year)
              :date (date)}]
    (main/info "Generating a project called" name
               "based on the 'whittemore' template.")
    (->files data
             ["project.clj" (render "project.clj" data)]
             ["README.md" (render "README.md" data)]
             [".gitignore" (render "gitignore" data)]
             [".hgignore" (render "hgignore" data)]
             ["src/{{nested-dirs}}.clj" (render "core.clj" data)]
             ["test/{{nested-dirs}}_test.clj" (render "test.clj" data)]
             ["LICENSE" (render "LICENSE" data)]
             ["data/smoking.csv" (render "smoking.csv" data)]
             ["notebooks/front_door.ipynb" (raw "front_door.ipynb")])))

