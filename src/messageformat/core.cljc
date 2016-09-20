(ns messageformat.core
  (:import #?(:clj [java.util Locale])
           #?(:clj [com.ibm.icu.text MessageFormat]
              :cljs [goog.i18n MessageFormat]))
  #?(:cljs (:require-macros [messageformat.lib :as lib])
     :clj (:require [messageformat.lib :as lib]))
  (:refer-clojure :exclude [compile]))

(defn formatter
  [locale]
  #?(:clj {:locale (Locale. (name (:name locale)))}
     :cljs {:locale locale}))

#?(:cljs (def current-locale (atom :en)))

(defn compile
  [formatter s]
  #?(:clj (let [m (MessageFormat. s (:locale formatter))]
            (fn [params] (.format m (into {} (map (fn [[k v]] [(name k) v]) params)))))
     :cljs (let [m (MessageFormat. s)]
             (fn [params]
               (let [l (:locale formatter)]
                 (when-not (= (:name l) @current-locale)
                   (set! (.-select goog.i18n.pluralRules) (:plural l))
                   (reset! current-locale (:name l))))
               (.format m (clj->js params))))))

#?(:clj (lib/generate-clj)
   :cljs (lib/generate-cljs))

#?(:cljs (enable-console-print!))

(comment
  
  (def f1 (formatter locale-ga))
  (def f2 (formatter locale-en))
  (def m1 (compile f1 "Тута {COUNT, plural, =0{нет результатов :(} =1{ один результат!} few{# результата} other{# результатов}}"))
  (def m2 (compile f2 "There {COUNT, plural, =0{are no results :(} =1{ is reslut!} other{are # results}}"))

  (prn
   (m1 {:COUNT 1})
   (m1 {:COUNT 2})
   (m1 {:COUNT 3})
   (m1 {:COUNT 11})
   (m1 {:COUNT 5})
   (m1 {:COUNT 10})
   (m1 {:COUNT 22})

   (m2 {:COUNT 1})
   (m2 {:COUNT 2})
   (m2 {:COUNT 3})
   (m2 {:COUNT 4})
   (m2 {:COUNT 5})
   (m2 {:COUNT 10})
   (m2 {:COUNT 22})
   ))
