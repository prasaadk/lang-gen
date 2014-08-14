(ns lang-gen.core
  (:import
   (simplenlg.framework NLGFactory)
   (simplenlg.lexicon Lexicon)
   (simplenlg.realiser.english Realiser)
   (simplenlg.features Feature Tense))
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

;; Lexicon lexicon = Lexicon.getDefaultLexicon;
(def lexicon (Lexicon/getDefaultLexicon))

;; NLGFactory nlg-factory = new NLGFactory(lexicon)
(def nlg-factory (new NLGFactory lexicon))

;; Realiser realiser = new Realiser(lexicon);
(def realiser (new Realiser lexicon))

(defn realise-sentence [sentence]
  (.realiseSentence realiser (.createSentence nlg-factory sentence)))

(defn realise-sentence [phrase]
  (.realiseSentence realiser phrase))

(defn generateStatement [subject predicate object]
  (let [p (.createClause nlg-factory)]
    (.setSubject p subject)
    (.setVerb p predicate)
    (.setObject p object)
    (.setFeature p Feature/TENSE Tense/FUTURE)
    (realise-sentence p)))

;;this statement returns "Mary will love cat"
(generateStatement "Mary" "love" "cat")

