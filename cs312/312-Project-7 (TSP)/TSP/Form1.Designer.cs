namespace TSP
{
    partial class Form1
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.toolStrip1 = new System.Windows.Forms.ToolStrip();
            this.toolStripLabel1 = new System.Windows.Forms.ToolStripLabel();
            this.txtSeed = new System.Windows.Forms.ToolStripTextBox();
            this.toolStripLabel5 = new System.Windows.Forms.ToolStripLabel();
            this.lblProblem = new System.Windows.Forms.ToolStripLabel();
            this.toolStripSeparator3 = new System.Windows.Forms.ToolStripSeparator();
            this.btnRun = new System.Windows.Forms.ToolStripButton();
            this.toolStripSeparator1 = new System.Windows.Forms.ToolStripSeparator();
            this.bNewProblem = new System.Windows.Forms.ToolStripButton();
            this.toolStripSeparator2 = new System.Windows.Forms.ToolStripSeparator();
            this.toolStripLabel2 = new System.Windows.Forms.ToolStripLabel();
            this.tbProblemSize = new System.Windows.Forms.ToolStripTextBox();
            this.toolStripSeparator5 = new System.Windows.Forms.ToolStripSeparator();
            this.toolStripLabel3 = new System.Windows.Forms.ToolStripLabel();
            this.tbCostOfTour = new System.Windows.Forms.ToolStripTextBox();
            this.toolStripSeparator4 = new System.Windows.Forms.ToolStripSeparator();
            this.Max = new System.Windows.Forms.ToolStripLabel();
            this.tbElapsedTime = new System.Windows.Forms.ToolStripTextBox();
            this.toolStripLabel6 = new System.Windows.Forms.ToolStripLabel();
            this.tbCurrentSize = new System.Windows.Forms.ToolStripTextBox();
            this.toolStripLabel7 = new System.Windows.Forms.ToolStripLabel();
            this.tbMaxSize = new System.Windows.Forms.ToolStripTextBox();
            this.toolStrip1.SuspendLayout();
            this.SuspendLayout();
            // 
            // toolStrip1
            // 
            this.toolStrip1.Dock = System.Windows.Forms.DockStyle.Bottom;
            this.toolStrip1.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.btnRun,
            this.toolStripSeparator1,
            this.bNewProblem,
            this.toolStripSeparator3,
            this.toolStripLabel1,
            this.txtSeed,
            this.toolStripLabel5,
            this.lblProblem,
            this.toolStripSeparator2,
            this.toolStripLabel2,
            this.tbProblemSize,
            this.toolStripSeparator5,
            this.toolStripLabel3,
            this.tbCostOfTour,
            this.toolStripSeparator4,
            this.toolStripLabel6,
            this.tbElapsedTime,
            this.toolStripLabel7,
            this.tbCurrentSize,
            this.Max,
            this.tbMaxSize});
            this.toolStrip1.LayoutStyle = System.Windows.Forms.ToolStripLayoutStyle.Flow;
            this.toolStrip1.Location = new System.Drawing.Point(0, 268);
            this.toolStrip1.Name = "toolStrip1";
            this.toolStrip1.Size = new System.Drawing.Size(488, 71);
            this.toolStrip1.TabIndex = 0;
            this.toolStrip1.Text = "toolStrip1";
            // 
            // toolStripLabel1
            // 
            this.toolStripLabel1.Name = "toolStripLabel1";
            this.toolStripLabel1.Size = new System.Drawing.Size(32, 15);
            this.toolStripLabel1.Text = "Seed";
            // 
            // txtSeed
            // 
            this.txtSeed.Name = "txtSeed";
            this.txtSeed.Size = new System.Drawing.Size(50, 23);
            // 
            // toolStripLabel5
            // 
            this.toolStripLabel5.Name = "toolStripLabel5";
            this.toolStripLabel5.Size = new System.Drawing.Size(55, 15);
            this.toolStripLabel5.Text = "Problem:";
            // 
            // lblProblem
            // 
            this.lblProblem.AutoSize = false;
            this.lblProblem.ImageAlign = System.Drawing.ContentAlignment.MiddleLeft;
            this.lblProblem.Name = "lblProblem";
            this.lblProblem.Size = new System.Drawing.Size(50, 22);
            this.lblProblem.Text = "--";
            this.lblProblem.TextAlign = System.Drawing.ContentAlignment.MiddleLeft;
            // 
            // toolStripSeparator3
            // 
            this.toolStripSeparator3.Name = "toolStripSeparator3";
            this.toolStripSeparator3.Size = new System.Drawing.Size(6, 23);
            // 
            // btnRun
            // 
            this.btnRun.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.btnRun.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.btnRun.Name = "btnRun";
            this.btnRun.Size = new System.Drawing.Size(32, 19);
            this.btnRun.Text = "Run";
            this.btnRun.Click += new System.EventHandler(this.btnRun_Click);
            // 
            // toolStripSeparator1
            // 
            this.toolStripSeparator1.Name = "toolStripSeparator1";
            this.toolStripSeparator1.Size = new System.Drawing.Size(6, 23);
            // 
            // bNewProblem
            // 
            this.bNewProblem.DisplayStyle = System.Windows.Forms.ToolStripItemDisplayStyle.Text;
            this.bNewProblem.ImageTransparentColor = System.Drawing.Color.Magenta;
            this.bNewProblem.Name = "bNewProblem";
            this.bNewProblem.Size = new System.Drawing.Size(81, 19);
            this.bNewProblem.Text = "new problem";
            this.bNewProblem.Click += new System.EventHandler(this.bNewProblem_Click);
            // 
            // toolStripSeparator2
            // 
            this.toolStripSeparator2.Name = "toolStripSeparator2";
            this.toolStripSeparator2.Size = new System.Drawing.Size(6, 23);
            // 
            // toolStripLabel2
            // 
            this.toolStripLabel2.Name = "toolStripLabel2";
            this.toolStripLabel2.Size = new System.Drawing.Size(75, 15);
            this.toolStripLabel2.Text = "Problem Size";
            // 
            // tbProblemSize
            // 
            this.tbProblemSize.Name = "tbProblemSize";
            this.tbProblemSize.Size = new System.Drawing.Size(50, 23);
            this.tbProblemSize.Text = "20";
            this.tbProblemSize.TextBoxTextAlign = System.Windows.Forms.HorizontalAlignment.Right;
            // 
            // toolStripSeparator5
            // 
            this.toolStripSeparator5.Name = "toolStripSeparator5";
            this.toolStripSeparator5.Size = new System.Drawing.Size(6, 23);
            // 
            // toolStripLabel3
            // 
            this.toolStripLabel3.Name = "toolStripLabel3";
            this.toolStripLabel3.Size = new System.Drawing.Size(70, 15);
            this.toolStripLabel3.Text = "Cost of tour";
            // 
            // tbCostOfTour
            // 
            this.tbCostOfTour.Name = "tbCostOfTour";
            this.tbCostOfTour.ReadOnly = true;
            this.tbCostOfTour.Size = new System.Drawing.Size(100, 23);
            this.tbCostOfTour.Text = "--";
            this.tbCostOfTour.TextBoxTextAlign = System.Windows.Forms.HorizontalAlignment.Right;
            // 
            // toolStripSeparator4
            // 
            this.toolStripSeparator4.Name = "toolStripSeparator4";
            this.toolStripSeparator4.Size = new System.Drawing.Size(6, 23);
            // 
            // Max
            // 
            this.Max.Name = "Max";
            this.Max.Size = new System.Drawing.Size(52, 15);
            this.Max.Text = "Max Size";
            // 
            // tbElapsedTime
            // 
            this.tbElapsedTime.Name = "tbElapsedTime";
            this.tbElapsedTime.ReadOnly = true;
            this.tbElapsedTime.Size = new System.Drawing.Size(100, 23);
            this.tbElapsedTime.Text = "--";
            this.tbElapsedTime.TextBoxTextAlign = System.Windows.Forms.HorizontalAlignment.Right;
            // 
            // toolStripLabel6
            // 
            this.toolStripLabel6.Name = "toolStripLabel6";
            this.toolStripLabel6.Size = new System.Drawing.Size(58, 15);
            this.toolStripLabel6.Text = "Solved in ";
            // 
            // tbCurrentSize
            // 
            this.tbCurrentSize.Name = "tbCurrentSize";
            this.tbCurrentSize.ReadOnly = true;
            this.tbCurrentSize.Size = new System.Drawing.Size(100, 23);
            this.tbCurrentSize.Text = "--";
            this.tbCurrentSize.TextBoxTextAlign = System.Windows.Forms.HorizontalAlignment.Right;
            // 
            // toolStripLabel7
            // 
            this.toolStripLabel7.Name = "toolStripLabel7";
            this.toolStripLabel7.Size = new System.Drawing.Size(27, 15);
            this.toolStripLabel7.Text = "Size";
            // 
            // tbMaxSize
            // 
            this.tbMaxSize.Name = "tbMaxSize";
            this.tbMaxSize.ReadOnly = true;
            this.tbMaxSize.Size = new System.Drawing.Size(100, 23);
            this.tbMaxSize.Text = "--";
            this.tbMaxSize.TextBoxTextAlign = System.Windows.Forms.HorizontalAlignment.Right;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(488, 339);
            this.Controls.Add(this.toolStrip1);
            this.Name = "Form1";
            this.Text = "Traveling Sales Person";
            this.Paint += new System.Windows.Forms.PaintEventHandler(this.Form1_Paint);
            this.Resize += new System.EventHandler(this.Form1_Resize);
            this.toolStrip1.ResumeLayout(false);
            this.toolStrip1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.ToolStrip toolStrip1;
        private System.Windows.Forms.ToolStripLabel toolStripLabel1;
        private System.Windows.Forms.ToolStripTextBox txtSeed;
        private System.Windows.Forms.ToolStripButton btnRun;
        private System.Windows.Forms.ToolStripButton bNewProblem;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator3;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator1;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator2;
        private System.Windows.Forms.ToolStripLabel toolStripLabel2;
        private System.Windows.Forms.ToolStripTextBox tbProblemSize;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator5;
        private System.Windows.Forms.ToolStripLabel toolStripLabel3;
        public System.Windows.Forms.ToolStripTextBox tbCostOfTour;
        private System.Windows.Forms.ToolStripSeparator toolStripSeparator4;
        private System.Windows.Forms.ToolStripLabel Max;
        public System.Windows.Forms.ToolStripTextBox tbElapsedTime;
        private System.Windows.Forms.ToolStripLabel toolStripLabel5;
        private System.Windows.Forms.ToolStripLabel lblProblem;
        private System.Windows.Forms.ToolStripLabel toolStripLabel6;
        public System.Windows.Forms.ToolStripTextBox tbCurrentSize;
        private System.Windows.Forms.ToolStripLabel toolStripLabel7;
        public System.Windows.Forms.ToolStripTextBox tbMaxSize;

    }
}
