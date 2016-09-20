(defproject messageformat "0.1.2"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :min-lein-version "2.5.3"
  :dependencies [[com.ibm.icu/icu4j "56.1"]]

  :plugins [[lein-figwheel "0.5.0-6"]
            [lein-cljsbuild "1.1.2" :exclusions [[org.clojure/clojure]]]]

  :profiles {:dev {:dependencies [[org.clojure/clojure "1.7.0"]
                                  [org.clojure/clojurescript "1.7.170"]]}}

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src"]
                :figwheel {}
                :compiler {:main messageformat.core
                           :asset-path "js/compiled/out"
                           :output-to "resources/public/js/compiled/messageformat.js"
                           :output-dir "resources/public/js/compiled/out"
                           :source-map-timestamp true}}
               ;; This next build is an compressed minified build for
               ;; production. You can build this with:
               ;; lein cljsbuild once min
               {:id "min"
                :source-paths ["src"]
                :compiler {:output-to "resources/public/js/compiled/messageformat.js"
                           :main messageformat.core
                           :optimizations :advanced
                           :pretty-print false}}]}

  :figwheel {})
