using System;
using System.Diagnostics;
using System.Collections.Generic;
using System.Drawing;

namespace VisualIntelligentScissors
{
    public class SimpleScissors : Scissors
    {
        Pen yellowpen = new Pen(Color.Yellow);
	    public SimpleScissors() { }
	    public SimpleScissors(GrayBitmap image, Bitmap overlay) : base(image, overlay) { }

	    public override void FindSegmentation(IList<Point> points, Pen pen)
	    {
		    if (Image == null) throw new InvalidOperationException("Set Image property first.");

            colorInitialPoints(points); // color circles around the points
            for(int i = 0; i < points.Count - 1; i++) // draw lines between each segments
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
                Program.MainForm.RefreshImage();
        }

        private void drawPath(Point start, Point end)
        {
            Point nextPoint = start;
            Dictionary<Point, Boolean> visited = new Dictionary<Point, Boolean>();

            while (true)
            {
                Overlay.SetPixel(nextPoint.X, nextPoint.Y, Color.Red);
                visited.Add(nextPoint, true);

                if (nextPoint.Equals(end))
                    return; // found end-point
                
                int nextPointWeight = int.MaxValue;

                // calculate neighboring points values
                Point pNorth = new Point(nextPoint.X, nextPoint.Y + 1);
                Point pEast = new Point(nextPoint.X + 1, nextPoint.Y);
                Point pSouth = new Point(nextPoint.X, nextPoint.Y - 1);
                Point pWest = new Point(nextPoint.X - 1, nextPoint.Y);

                // calculate appropriate weights
                int wNorth = this.GetPixelWeight(pNorth);
                int wEast = this.GetPixelWeight(pEast);
                int wSouth = this.GetPixelWeight(pSouth);
                int wWest = this.GetPixelWeight(pWest);

                // for each point make sure it is in the overlay and then pick the one with the biggest weight 
                // in North, East, South, West order
                if (inOverlay(pNorth) && !visited.ContainsKey(pNorth) && wNorth < nextPointWeight)
                {
                    nextPoint = pNorth;
                    nextPointWeight = wNorth;
                }

                if (inOverlay(pEast) && !visited.ContainsKey(pEast) && wEast < nextPointWeight)
                {
                    nextPoint = pEast;
                    nextPointWeight = wEast;
                }

                if (inOverlay(pSouth) && !visited.ContainsKey(pSouth) && wSouth < nextPointWeight)
                {
                    nextPoint = pSouth;
                    nextPointWeight = wSouth;
                }

                if (inOverlay(pWest) && !visited.ContainsKey(pWest) && wWest < nextPointWeight)
                {
                    nextPoint = pWest;
                    nextPointWeight = wWest;
                }
                // if the nextPointWeight was never changed (all the neighboring points have been visited = dead end)
                if (nextPointWeight == int.MaxValue)
                    break;
            }
        }

        // checks that point is in overlay
        private Boolean inOverlay(Point p)
        {
            return p.X < Overlay.Width - 2 &&
                p.Y < Overlay.Height - 2 &&
                p.X > 1 && p.Y > 1;
        }
    }
}
