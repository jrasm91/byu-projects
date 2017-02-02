using System;
using System.Diagnostics;
using System.Collections.Generic;
using System.Drawing;
using System.Collections; 

namespace VisualIntelligentScissors
{
    public class DijkstraScissors : Scissors
    {
        public DijkstraScissors() { }
        public DijkstraScissors(GrayBitmap image, Bitmap overlay) : base(image, overlay) { }
        Pen yellowpen = new Pen(Color.Yellow);
	    public override void FindSegmentation(IList<Point> points, Pen pen)
	    {
		    if (Image == null) throw new InvalidOperationException("Set Image property first.");

            colorInitialPoints(points); // colors the circles around the points
            for(int i = 0; i < points.Count; i++) // draws each path segment
                drawPath(points[i], points[(i + 1) % points.Count]);
	    }
        private void colorInitialPoints(IList<Point> points)
        {
            using (Graphics g = Graphics.FromImage(Overlay))
            {
                for (int i = 0; i < points.Count; i++)
                {
                    Point start = points[i];
                    Point end = points[(i + 1) % points.Count];
                    g.DrawEllipse(yellowpen, start.X, start.Y, 5, 5);
                }
            }
        }

        private void drawPath(Point start, Point end)
        {
            // priority queue based off of weights and then x,y positions
            SortedSet<Node> queue = new SortedSet<Node>();
            // dictionary for who was settled  what point (used for backtrace)
            Dictionary<Point, Point> settled = new Dictionary<Point, Point>();

            queue.Add(new Node(start, this.GetPixelWeight(start), start));
            while (queue.Count >= 0 || queue.Count > 1000)
            {
                Node next = queue.Min;
                queue.Remove(next);
                if (settled.ContainsKey(next.p))
                    continue;
                else
                    settled.Add(next.p, next.settledBy);
                if (next.p == (end))
                    break; // found end-point

                // add the four neighboring points to the queue if in overlay and not settled

                Point pNorth = new Point(next.p.X, next.p.Y + 1);
                if (inOverlay(pNorth) && !settled.ContainsKey(pNorth))
                    queue.Add(new Node(pNorth, next.w + this.GetPixelWeight(pNorth), next.p));
                
                Point pSouth = new Point(next.p.X, next.p.Y - 1);
                if (inOverlay(pSouth) && !settled.ContainsKey(pSouth))
                    queue.Add(new Node(pSouth, next.w + this.GetPixelWeight(pSouth), next.p));
                
                Point pWest = new Point(next.p.X - 1, next.p.Y);
                if (inOverlay(pWest) && !settled.ContainsKey(pWest))
                    queue.Add(new Node(pWest, next.w + this.GetPixelWeight(pWest), next.p));
                
                Point pEast = new Point(next.p.X + 1, next.p.Y);
                if (inOverlay(pEast) && !settled.ContainsKey(pEast))
                    queue.Add(new Node(pEast, next.w + this.GetPixelWeight(pEast), next.p));
            }

            // backtrace (start with last point and see who settled it, 
            // then recurse and do the same until you are arrive at the start point
            Point trace = end;
            while(true)
            {
                Overlay.SetPixel(trace.X, trace.Y, Color.Red);
                if(trace.Equals(start))
                    break;
                trace = settled[trace];
            }
            //Program.MainForm.RefreshImage();
        }

        // check to see if point is in the overlay
        private Boolean inOverlay(Point p)
        {
            return p.X < Overlay.Width - 2 &&
                p.Y < Overlay.Height - 2 &&
                p.X > 0 && p.Y > 0;
        }

        // class used in queue to wrap x,y point, weight, and keep track of what it was settled by
        private class Node : IComparable<Node>
        {
            public Point p;
            public int w;
            public Point settledBy;
            public Node(Point p, int w, Point settledBy)
            {
                this.p = p;
                this.w = w;
                this.settledBy = settledBy;
            }
            public int CompareTo(Node obj)
            {
                if (obj == null) return 1;
                Node otherNode = obj as Node;
                if (otherNode != null)
                {
                    // sorts points based off of weights and then x,y coordinates
                    if (this.w != otherNode.w)
                        return this.w.CompareTo(otherNode.w);
                    if (this.p.Y != otherNode.p.Y)
                        return this.p.Y.CompareTo(otherNode.p.Y);
                    if (this.p.X != otherNode.p.X)
                        return this.p.X.CompareTo(otherNode.p.X);
                    else
                        return 0;
                }
                else
                    throw new ArgumentException("Object is not a Node");
            }
            public override bool Equals(object obj)
            {
                if (obj == null) return false;
                Node otherNode = obj as Node;
                return this.p.Equals(otherNode.p);
            }

            public override string ToString()
            {
                return base.ToString();
            }

            public override int GetHashCode()
            {
                return this.w.GetHashCode() + this.p.GetHashCode();
            }
        }
    }
}
