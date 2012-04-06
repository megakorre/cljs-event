# cljs-event

a libary that is intended to help with dom event
handeling from the repl

## to use it add a ref to

```clojure
[cljs-event "0.1.0-SNAPSHOT"]

## and import the following

```clojure
(ns something
 (:require [cljs-event.core :as event])
 (:use-macros [cljs-event.macros :only [defhandler]]))


## and run 

```clojure
(event/setup)

now you can write handlers like this

```clojure
(defhandler :some-event :my-ref [e]
 (js/alert "some event happend"))

The ref theres is what enables you to re evaluate the handler from the
repl. So anny handler new handler that gets defined with the same ref
replaces the old one

Now to make a dom element call that handler add the class .event
and add the attribute data-event with this format
"event-name:handler-name"

## example
```clojure
[:input.event { :type "button" :data-event "click:some-event" :value
"click me" }]

this will cause the input to trigger the handler.


