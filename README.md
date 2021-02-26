# GameAlgorithms
This is a project containing three algorithms I implemented for a java game I'm working on.
I wanted to create a separate repository to show off these standalone algorithms. I apologize
for the lack of comments.

# Sweep and Prune
This is a broadphase algorithm for keeping track of all objects in a world that have overlapping bounding boxes.
I first learned about this algorithm from [this paper](http://www.codercorner.com/SAP.pdf) which I found through ThinMatrix.
My first implementation was pretty sloppy, but this is my third implementation and I think I got it pretty clean this time around.
It's an abstract class now designed to work with whichever custom application. I did decide to use integer values because it was
the most useful for me, but I'm sure it would work otherwise.

# Gilbert Johnson Keerthi
This is wicked awesome narrowphase algorithm for determining if two convex objects are overlapping. It uses mathematical wizardry
called the minkowski sum to join objects in a way where the resulting shape only overlaps the origin if the shapes are overlapping.
I learned about it from Casey Muratori and his video [here](https://caseymuratori.com/blog_0003). My implementation again uses
integers instead of floats, and is unlike the ones I've heard of in that it excludes objects that are touching but not overlapping.

# Octree
This is a neat algorithm for storing world objects in an easy to query way. I forget where I heard about it but the concept seemed
doable enough so I implemented it from scratch.

## Thanks for viewing!
