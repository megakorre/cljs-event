(ns cljs-event.core
  (:use [jayq.core :only [$ append]]))

(def events "blur focus load resize scroll unload
beforeunload click dblclick mousedown mouseup mousemove
mouseover mouseout mouseenter mouseleave change select
submit keydown keypress keyup error ")

(def event-handlers (atom {}))

(defn attach-handler
  [event-name ref fun]
  (if (nil? (@event-handlers (name event-name)))
    (swap! event-handlers assoc (name event-name) {}))
  (swap! event-handlers update-in [(name event-name)] assoc ref fun))

(defn handler [e]
  (let [src (.-srcElement e)
        [event-type event-name] (.split (.data ($ src) "event") ":")]
    (when (= event-type (.-type e))
      (doseq [[key value] (@event-handlers event-name)]  
        (apply value [e])))))

(defn setup []
  (let [$body ($ "body")]
    (.delegate $body ".event" events handler)))



