(ns cljs-event.macros)

(defmacro defhandler [event-name ref-name args & code]
  `(cljs-event.core/attach-handler
    ~event-name
    ~ref-name
    (fn ~args ~@code)))

