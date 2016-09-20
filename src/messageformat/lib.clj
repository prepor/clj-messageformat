(ns messageformat.lib
  (:require [clojure.string :as str]))

(def plurals
  {:af :en
   :am :hi
   :ar :ar
   :az :es
   :be :be
   :bg :es
   :bn :hi
   :br :br
   :bs :sr
   :ca :en
   :chr :es
   :cs :cs
   :cy :cy
   :da :da
   :de :en
   :de_AT :en :de-AT :en
   :de_CH :en :de-CH :en
   :el :es
   :en :en
   :en_AU :en :en-AU :en
   :en_CA :en :en-CA :en
   :en_GB :en :en-GB :en
   :en_IE :en :en-IE :en
   :en_IN :en :en-IN :en
   :en_SG :en :en-SG :en
   :en_US :en :en-US :en
   :en_ZA :en :en-ZA :en
   :es :es
   :es_419 :es :es-419 :es
   :es_ES :es :es-ES :es
   :es_MX :es :es-MX :es
   :es_US :es :es-US :es
   :et :en
   :eu :es
   :fa :hi
   :fi :en
   :fil :fil
   :fr :fr
   :fr_CA :fr :fr-CA :fr
   :ga :ga
   :gl :en
   :gsw :es
   :gu :hi
   :haw :es
   :he :he
   :hi :hi
   :hr :sr
   :hu :es
   :hy :fr
   :id :default
   :in :default
   :is :is
   :it :en
   :iw :he
   :ja :default
   :ka :es
   :kk :es
   :km :default
   :kn :hi
   :ko :default
   :ky :es
   :ln :ak
   :lo :default
   :lt :lt
   :lv :lv
   :mk :mk
   :ml :es
   :mn :es
   :mo :ro
   :mr :hi
   :ms :default
   :mt :nt
   :my :default
   :nb :es
   :ne :es
   :nl :en
   :no :es
   :no_NO :es :no-NO :es
   :or :es
   :pa :ak
   :pl :pl
   :pt :pt
   :pt_BR :pt :pt-BR :pt
   :pt_PT :pt_PT :pt-PT :pt_PT
   :ro :ro
   :ru :ru
   :sh :sr
   :si :si
   :sk :cs
   :sl :sl
   :sq :es
   :sr :sr
   :sr_Latn :sr :sr-Latn :sr
   :sv :en
   :sw :en
   :ta :es
   :te :es
   :th :default
   :tl :fil
   :tr :es
   :uk :ru
   :ur :en
   :uz :es
   :vi :default
   :zh :default
   :zh_CN :default :zh-CN :default
   :zh_HK :default :zh-HK :default
   :zk_TW :default :zk-TW :default
   :zu :hi})

(defn compile-locale?
  []
  (if-let [locales (System/getenv "LOCALES")]
    (let [l (->> (str/split #"," locales)
                 (map keyword)
                 (set))]
      (fn [locale] (contains? l locale)))
    (constantly true)))


(defmacro generate-cljs
  []
  `(do
     ~@(let [checker (compile-locale?)]
         (for [[locale plural] plurals
               :when (checker locale)]
           `(def ~(symbol (str "locale-" (name locale)))
              {:name ~locale
               :plural ~(symbol (str "goog.i18n.pluralRules." (name plural) "Select_"))})))))

(defmacro generate-clj
  []
  `(do
     ~@(for [[locale plural] plurals]
         `(def ~(symbol (str "locale-" (name locale)))
            {:name ~locale}))))
