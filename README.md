# Link Sanitizer

## About

A small Android application to clean up links you click on in other apps.

I decided to write this app when I noticed a variety of useless links appearing in my browser history.
By 'useless links', I mean things like links to URL shorteners, amp links, tracking links, and so forth.

This app is an attempt to get rid of them. It serves as your default browser
(though once it does its work it redirects to your browser of choice)
and intercepts each web link you click on in your email client, in your maps app, in your podcast player,
in your social media apps, and wherever links are to be found (other than in a web browser).

This app automatically follows redirects (where appropriate), simplifies URLs,
dodges tracking where it can, and generally tries to make the links you come across a little better. 

## Installation

I haven't built an APK for you to use yet, so at this point you'll have to compile it yourself.
I don't anticipate that to be terribly difficult if this repository is imported into Android Studio.

## Development

This app has been designed with development in mind.
If there's something in particular you would like to do with incoming links,
then just create a subclass of `UriHandler` to do whatever it is that you'd like.
It's potentially even simpler than it sounds: most of what you have to do is implement a single method,
`backgroundTask`, which takes a `Uri` and returns a different (or the same) `Uri`.
You can do anything you'd like in this method, though I anticipate one will typically want to do
simple string transformations on the `Uri`s or make simple HTTP requests.
Both of these are done in existing subclasses, so take a peek for inspiration.

Once you've made your subclass, all you have to do is insert it into the processing queue.
At the moment, you'll have to find this queue in the `SanitizeActivity.kt` file,
sandwiched between the `FirstHandler` and the `FinalHandler`.
Just place your class in a variable between those,
and make sure your constructor takes the variable above it as an argument
and that the constructor below yours takes your class as its argument.
Note that the closer your class is to `FirstHandler`, the sooner it will be called.
In the (near) future I would like to clean this up and make it even easier to interact with.