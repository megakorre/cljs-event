(ns cljs-event.core
  (:use [jayq.core :only [$ append]]))

(def events "blur focus load resize scroll unload
beforeunload click dblclick mousedown mouseup mousemove
mouseover mouseout mouseenter mouseleave change select
submit keydown keypress keyup error ")

(def ^{ :doc "holds all event handlers" }
  event-handlers (atom {}))

(defn attach-handler
  [event-name ref fun]
  (if (nil? (@event-handlers (name event-name)))
    (swap! event-handlers assoc (name event-name) {}))
  (swap! event-handlers update-in [(name event-name)] assoc ref fun))

(defn trigger
  "manually trigger some event"
  [event-name data]
  (doseq [[key value] (@event-handlers (name event-name))]  
    (apply value [data])))

(defn handler
  "the standard callback for all events"
  [e]
  (let [src (.-srcElement e)
        [event-type event-name] (.split (.data ($ src) "event") ":")]
    (when (= event-type (.-type e))
      (trigger (symbol event-name) e))))

(defn setup
  "registers the dom listeners
    extra-events: adds extra dom events to listen to
                  default events is under cljs-event.core/events
  "
  [extra-events]
  (let [$body ($ "body")
        events (if extra-events
                 (str events " " extra-events)
                 events)]
    (.delegate $body ".event" events handler)))


(defn triggers
  "creates the data-event string and assoc's it
   with :data-event

    ex:

     [:input (triggers :click :some-event { :type \"button\" :value \"click me\" })]
  "
  [dom-event event-triggered m]
  (assoc m :data-event
         (str (name dom-event) ":" (name event-triggered))))


