# Link Sanitizer

## Important

Please note that the main development takes place on GitLab [here](https://gitlab.com/eli173/linksanitizer), and this repository is just a mirror. I would appreciate if comments, bug reports, and suggestions were submitted there rather than here.

## About

A small, open-source (GPLv3) Android application to clean up links you click on in other apps.

I decided to write this app when I noticed a variety of useless links appearing in my browser history.
By 'useless links', I mean things like links to URL shorteners, amp links, tracking links, and so forth.

This app is an attempt to get rid of them. It serves as your default browser
(though once it does its work it redirects to your browser of choice)
and intercepts each web link you click on in your email client, in your maps app, in your podcast player,
in your social media apps, and wherever links are to be found (other than in a web browser).

This app automatically follows redirects (where appropriate), simplifies URLs,
dodges tracking where it can, and generally tries to make the links you come across a little better. 

## Installation

Building this from Android Studio is hopefully very straightforward, but I've also provided an APK
on [my website](https://prog.eli173.com/linksanitizer/) for convenience
until I get this app on F-Droid and/or the Play Store.

## Development

This app has been designed with development in mind.
If there's something in particular you would like to do with incoming links,
then just create a subclass of `UriHandler` to do whatever it is that you'd like.
It's potentially even simpler than it sounds: most of what you have to do is implement a single method,
`backgroundTask`, which takes a `Uri` and returns a different (or the same) `Uri`.
You can do anything you'd like in this method, though I anticipate one will typically want to do
simple string transformations on the `Uri`s or make simple HTTP requests.
Both of these are done in existing subclasses, so take a peek for inspiration.

Once you've made your subclass, all you have to do is pick an identifying `classString` 
and insert your class into the processing queue.
At the moment, you'll have to find this queue in the `SanitizeActivity.kt` file,
sandwiched between the `FirstHandler` and the `FinalHandler`.
Just place your class in a variable between those,
and make sure your constructor takes the variable above it as an argument
and that the constructor below yours takes your class as its argument.
Note that the closer your class is to `FirstHandler`, the sooner it will be called.
In the (near) future I would like to clean this up and make it even easier to interact with.

Please note that I have generally employed logging such that a report is made whenever the Uri is changed,
so that the changes made as the UriHandlers operate are clear to anyone working on something new.
Feel free to use logging however you like,
but keep in mind any information that might be useful for other developers.
