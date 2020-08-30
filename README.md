/* *****************************************************************************
 *  Name: Mohammad Alqudah
 *  NetID: malqudah
 *  Precept: P05
 *
 *  Hours to complete assignment (optional):
 *
 **************************************************************************** */

Programming Assignment 5: Kd-Trees


/* *****************************************************************************
 *  Describe the Node data type you used to implement the
 *  2d-tree data structure.
 **************************************************************************** */
each node in the kd tree is comprised of a point, a generic value, a rectangle
corresponding to the bound of the point, a pointer to the left/bottom subtree
(depending on if you compare via x or y values) and a pointer to the right/top
subtree. the orientation of each node was not assigned in the node constructor,
but rather it was assigned through a boolean variable within the put method.
/* *****************************************************************************
 *  Describe your method for range search in a kd-tree.
 **************************************************************************** */
to search for the points that are inside a given query rectangle, first created
a private helper method to handle the majority of the work;, which takes in a
 rectangle, a node, and a queue. after doing the
respective null checks, create a queue that is to hold the points that are
within the rectangle. then call the helper method, which includes the various
cases of intersection/child node intersection:
if the query rectangle does not intersect the node rectangle, return. similarly,
if the query rectangle contains the node point (calculated via the RectHV data
structure), then add this point to the aforementioned queue. lastly, recursively
call the function for both children of the node in question and continue the
search.

/* *****************************************************************************
 *  Describe your method for nearest neighbor search in a kd-tree.
 **************************************************************************** */
to search for the nearest neighbor to a query point in a kd tree, first
conducted the necessary null checks and then delegate most of the work to
a private helper function. the privte helper function takes in a point,
a champion point to keep track of the nearest neighbor, a node and a boolean
vertical to keep track of the orientation of the node in question. avoids
pruning via a check between the distance of the rect of a node and the query pt
and the distance between the query point and the point of a node. in addition,
immediately update the champion if the point of the node in question is closer
to the query than the champion.
i used an int cmp to keep track of which direction to go into, depending on the
orientation of the parent node instead of the compareto built into point2d
to be specific about the y and x coordinates, as the compareto in point2d doesnt
do this. then recursively call the function depending on the value of cmp,
and update the champion accordingly.

/* *****************************************************************************
 *  How many nearest-neighbor calculations can your PointST implementation
 *  perform per second for input1M.txt (1 million points), where the query
 *  points are random points in the unit square?
 *
 *  Fill in the table below, using one digit after the decimal point
 *  for each entry. Use at least 1 second of CPU time.
 *  (Do not count the time to read the points or to build the 2d-tree.)
 *
 *  Repeat the same question but with your KdTreeST implementation.
 *
 **************************************************************************** */

i intentionally used -xint to make the test times longer than one second,because
when i tried to test my classes using stopwatchcpu without xint they kept
taking less time than one second (0.000064 seconds for KDTree and 0.091381
seconds for PointST). when i used -xint, i got 1.6 seconds for PointST and
0.00052 seconds for KDtreeST. this is still smaller than the 1 second specified,
but I couldnt figure out any other way to make the time larger. I tried asking
fellow students as well as lab TAs (how to deal with the seconds issue, not
 specifics of the timing) and they had no suggestions.


                 # calls to         /   CPU time     =   # calls to nearest()
                 client nearest()       (seconds)        per second
                ------------------------------------------------------
PointST:            1                   1.6                     0.625

KdTreeST:           29              5.2 * (10 ^ -5)             557,692

Note: more calls per second indicates better performance.


/* *****************************************************************************
 *  Did you fill out the mid-semester feedback form?
 *  If not, please do so now: https://forms.gle/72SRLDPmR49NJDM66
 **************************************************************************** */
 yes

/* *****************************************************************************
 *  Known bugs / limitations.
 **************************************************************************** */


/* *****************************************************************************
 *  Describe whatever help (if any) that you received.
 *  Don't include readings, lectures, and precepts, but do
 *  include any help from people (including course staff, lab TAs,
 *  classmates, and friends) and attribute them by name.
 **************************************************************************** */
Professor Ibrahim and Maia, Molly
Ryan lab TA


/* *****************************************************************************
 *  Describe any serious problems you encountered.
 **************************************************************************** */


/* *****************************************************************************
 *  If you worked with a partner, assert below that you followed
 *  the protocol as described on the assignment page. Give one
 *  sentence explaining what each of you contributed.
 **************************************************************************** */




/* *****************************************************************************
 *  List any other comments here. Feel free to provide any feedback
 *  on  how helpful the class meeting was and on how much you learned
 * from doing the assignment, and whether you enjoyed doing it.
 **************************************************************************** */
