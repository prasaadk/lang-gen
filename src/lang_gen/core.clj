(ns lang-gen.core
  (:import
   (simplenlg.framework NLGFactory)
   (simplenlg.lexicon Lexicon)
   (simplenlg.realiser.english Realiser)
   (simplenlg.features Feature Tense))
  (:require [lang-gen.opennlp :as nlp])
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

(defn generateStatement [subject predicate object tense]
  (let [p (.createClause nlg-factory)]
    (.setSubject p subject)
    (.setVerb p predicate)
    (.setObject p object)
    (.setFeature p Feature/TENSE tense)
    (realise-sentence p)))

(defn generateStatementWithComplement [subject predicate object tense & complements]
  (let [p (.createClause nlg-factory)]
    (.setSubject p subject)
    (.setVerb p predicate)
    (.setObject p object)
    (doseq [i complements] (.addComplement p i))    
    (.setFeature p Feature/TENSE tense)
    (realise-sentence p)))

;;this statement returns "Mary will love cat"
(print (generateStatement "Mary" "love" "cat" Tense/PAST) "\n")
(print (generateStatement "Mary" "love" "cat" Tense/FUTURE) "\n")
(print (generateStatement "Mary" "love" "cat" Tense/PRESENT) "\n")

(print (generateStatementWithComplement "Mary" "love" "cat" Tense/PRESENT "a lot" "and a little more" "to be honest") "\n")



