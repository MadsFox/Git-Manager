package git_manager.tool;

import git_manager.constants.ProjectDetails;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import processing.app.Base;
import processing.app.Editor;
import processing.app.Toolkit;

public class GitGUIFrame extends JFrame implements ActionListener {

	Editor editor;
	GitOperations gitops;
	JPanel panel;
	GitMenuBar menu;
	GitOptionToolbar tool;
	// static Point frameLocation;
	
	int mode = 0;

	private static final long serialVersionUID = 1L;

	public GitGUIFrame(Editor e) {

		editor = e;
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

		// setMinimumSize(new Dimension(600, 500));
		setTitle(ProjectDetails.NAME);
		setVisible(true);

		menu = new GitMenuBar();
		//setJMenuBar(menu); TODO: Uncomment this to display menubar

		tool = new GitOptionToolbar(editor);

	  panel = new JPanel(new GridBagLayout());
		  
		add(panel);

		GridBagConstraints c = new GridBagConstraints();

		c.ipady = 20;
		c.anchor = GridBagConstraints.PAGE_START;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 5;
		c.weighty = 1.0;
		c.weightx = 1;
		
		if (mode == 2) {
		  c.insets = new Insets(0, 0, 5, 0);
	    c.fill = GridBagConstraints.HORIZONTAL;
		}
		else {
		  c.insets = new Insets(0, 0, 0, 0);
	    c.fill = GridBagConstraints.BOTH;
		}

    panel.add(tool, c);

    this.setVisible(true);

		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		c.gridx = 1;
		c.gridy = 1;
		c.ipady = 0;
		c.gridwidth = 3;
		c.insets = new Insets(0, 0, 3, 0);
		c.weighty = 0;

		JButton bCreate = new JButton("Click to create repo");
		bCreate.addActionListener(this);
		if (mode == 2) {
		  panel.add(bCreate, c);
		}
		bCreate.setActionCommand("create");

		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 3;
		c.insets = new Insets(0, 0, 3, 0);

		JButton bSnap = new JButton("Click to \"Take Snapshot\"");
		bSnap.addActionListener(this);
    if (mode == 2) {
      panel.add(bSnap, c);
    }
		bSnap.setActionCommand("snapshot");

		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 3;
		c.insets = new Insets(0, 0, 3, 0);

		JButton bAdd = new JButton("Add all files");
		bAdd.addActionListener(this);
    if (mode == 2) {
      panel.add(bAdd, c);
    }
		bAdd.setActionCommand("add");

		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 1;
		c.insets = new Insets(0, 0, 3, 0);

		JButton bCommit = new JButton("Commit");
		bCommit.addActionListener(this);
		if (mode == 2) {
		  panel.add(bCommit, c);
		}
		bCommit.setActionCommand("commit");

		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 3;
		c.insets = new Insets(0, 0, 3, 0);

		JButton bLogin = new JButton("Push to GitHub");
		bLogin.addActionListener(this);
    if (mode == 2) {
      panel.add(bLogin, c);
    }
		bLogin.setActionCommand("push");

		Toolkit.setIcon(this);
		gitops = new GitOperations(editor);

		this.addWindowListener(new WindowAdapter() {
			// Invoked when a window is in the process of being closed.
			public void windowClosing(WindowEvent e) {
				dispose();
				GitManager.frame = null;
			}
		});
		if (mode == 2) {
      if (Base.isLinux()) {
        setMinimumSize(new Dimension(700, 530));
        setPreferredSize(new Dimension(700, 530));
      } else {
        setMinimumSize(new Dimension(650, 530));
        setPreferredSize(new Dimension(650, 530));
      }
      setResizable(true);
		}
		else {
		  this.pack();
		  setPreferredSize(new Dimension(this.getWidth() + 135, this.getHeight()));
		  setResizable(false); //TODO: Make true for expertise
		}
		
		this.pack();

		// frameLocation = getLocationOnScreen();
		//
		// this.addComponentListener(new ComponentListener() {
		//
		// @Override
		// public void componentShown(ComponentEvent arg0) {
		// frameLocation = getLocationOnScreen();
		// }
		//
		// @Override
		// public void componentResized(ComponentEvent arg0) {
		// frameLocation = getLocationOnScreen();
		// }
		//
		// @Override
		// public void componentMoved(ComponentEvent arg0) {
		// frameLocation = getLocationOnScreen();
		// }
		//
		// @Override
		// public void componentHidden(ComponentEvent arg0) {
		//
		// }
		// });

	}

	public String getMessage(String dialogText) {
		return JOptionPane.showInputDialog(panel, dialogText, null);
	}

	public void actionPerformed(ActionEvent e) {
		if ("create".equals(e.getActionCommand()))
			gitops.initRepo();
		else if ("snapshot".equals(e.getActionCommand()))
			gitops.addAndCommit(getMessage("Enter commit message"));
		else if ("add".equals(e.getActionCommand()))
			gitops.addFiles();
		else if ("commit".equals(e.getActionCommand()))
			gitops.commitChanges(getMessage("Enter commit message"));
		else if ("push".equals(e.getActionCommand())) {
			gitops.getUnameandPass();
			gitops.pushToRemote();
		}
	}

}
