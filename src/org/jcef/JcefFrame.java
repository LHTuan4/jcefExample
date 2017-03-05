package org.jcef;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefApp.CefAppState;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.handler.CefAppHandlerAdapter;
import org.cef.handler.CefLoadHandlerAdapter;

import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

import java.awt.Rectangle;
import java.awt.Dimension;
import java.awt.ComponentOrientation;

import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;


public class JcefFrame extends JFrame {
	private static final long serialVersionUID = -5570653778104813836L;
	private JTextField urlText;
	private final CefApp _cefApp;
	private final CefClient _client;
	private final CefBrowser _browser;
	private String url = "https://www.google.com/";
	Support supp = new Support();
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JcefFrame frame = new JcefFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public JcefFrame() throws IOException {
		getContentPane().setBackground(SystemColor.controlHighlight);
		getContentPane().setForeground(Color.WHITE);
		getContentPane().setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		setSize(new Dimension(800, 600));
		setBounds(new Rectangle(0, 0, 1024, 768));
		CefApp.addAppHandler(new CefAppHandlerAdapter(null) {
			@Override
			public void stateHasChanged(org.cef.CefApp.CefAppState state) {
				// Shutdown the app if the native CEF part is terminated
				if (state == CefAppState.TERMINATED){
					// calling System.exit(0) appears to be causing assert errors,
					// as its firing before all of the CEF objects shutdown.
					//System.exit(0);
				}
			}
		});
		
		
        
        JButton btnBack = new JButton("<");
        btnBack.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
        		if(_browser.canGoBack()){
        			_browser.goBack();
        		}
        	}
        });
        
        JButton btnFoward = new JButton(">");
        btnFoward.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
        		if(_browser.canGoForward()){
        			_browser.goForward();
        		}
        	}
        });
        
        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
        		_browser.reload();
        	}
        });
        
        
        urlText = new JTextField();
        urlText.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		if(e.getKeyCode() == KeyEvent.VK_ENTER){
        			_browser.loadURL(urlText.getText());
        		}
        		if(e.getKeyCode() == KeyEvent.VK_F5){
        			_browser.reload();
        		} 
        	}
        });
        
        urlText.setPreferredSize(new Dimension(6, 25));
        urlText.setMinimumSize(new Dimension(6, 25));
        urlText.setText(url);
        urlText.setColumns(10);
        
        JButton btnGo = new JButton("Go");
        btnGo.setAlignmentY(Component.TOP_ALIGNMENT);
        btnGo.setAlignmentX(Component.RIGHT_ALIGNMENT);
        btnGo.setAutoscrolls(true);
        btnGo.setVerticalAlignment(SwingConstants.TOP);
        btnGo.setHorizontalAlignment(SwingConstants.RIGHT);
        btnGo.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent arg0) {
        		if(urlText.getText()==null){
        			return;
        		}
        		else{
        			url = urlText.getText();
        			_browser.loadURL(url);
        		}
        	}
        });

		    addWindowListener(new WindowAdapter() {
		      @Override
		      public void windowClosing(WindowEvent e) {
		        CefApp.getInstance().dispose();
		        dispose();
		      }
		    });
		    CefSettings setting = new CefSettings();	
		    setting.windowless_rendering_enabled = false;
			_cefApp = CefApp.getInstance(setting);
			_client = _cefApp.getInstance().createClient();
			_browser = _client.createBrowser(url, false, false);
			final String script = Support.getScript("./resources/script.js");
			System.out.print(script);

			//_browser.getIdentifier();
			Component browserUI = _browser.getUIComponent();
	
			
			//_browser.getSource(arg0);
			
			JPanel pane = new JPanel();
			pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			pane.setAutoscrolls(true);
	        pane.setLayout(new BorderLayout());
	       
	        pane.add(browserUI, BorderLayout.CENTER);
	        _client.addLoadHandler(new CefLoadHandlerAdapter() {
	            @Override
	            public void onLoadingStateChange(CefBrowser _browser,
	                                             boolean isLoading,
	                                             boolean canGoBack,
	                                             boolean canGoForward) {
	              if (!isLoading) {
	                // The page has finished loading.
	                urlText.setText(_browser.getURL().toString());
	                // Do something with |url|
	              }
	            }
	            /* (non-Javadoc)
	             * @see org.cef.handler.CefLoadHandlerAdapter#onLoadEnd(org.cef.browser.CefBrowser, int, int)
	             */
	            public void onLoadEnd( CefBrowser browser,int frameIdentifier, int httpStatusCode){
            		_browser.executeJavaScript(script, "", 0);	 
	        		//_browser.executeJavaScript("document.getElementsByClassName('tsf')[0].submit();", "", 0);
	        		System.out.print("Finish load");
            	}
	        });
	        
	        
	        GroupLayout groupLayout = new GroupLayout(getContentPane());
	        groupLayout.setHorizontalGroup(
	        	groupLayout.createParallelGroup(Alignment.TRAILING)
	        		.addGroup(groupLayout.createSequentialGroup()
	        			.addContainerGap()
	        			.addComponent(btnBack, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
	        			.addPreferredGap(ComponentPlacement.RELATED)
	        			.addComponent(btnFoward, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
	        			.addPreferredGap(ComponentPlacement.RELATED)
	        			.addComponent(btnRefresh)
	        			.addPreferredGap(ComponentPlacement.RELATED)
	        			.addComponent(urlText, GroupLayout.DEFAULT_SIZE, 743, Short.MAX_VALUE)
	        			.addPreferredGap(ComponentPlacement.RELATED)
	        			.addComponent(btnGo)
	        			.addGap(17))
	        		.addComponent(pane, GroupLayout.DEFAULT_SIZE, 1006, Short.MAX_VALUE)
	        );
	        groupLayout.setVerticalGroup(
	        	groupLayout.createParallelGroup(Alignment.LEADING)
	        		.addGroup(groupLayout.createSequentialGroup()
	        			.addGap(14)
	        			.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
	        				.addComponent(urlText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	        				.addComponent(btnRefresh)
	        				.addComponent(btnFoward)
	        				.addComponent(btnBack)
	        				.addComponent(btnGo))
	        			.addGap(13)
	        			.addComponent(pane, GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE))
	        );
	        getContentPane().setLayout(groupLayout);
	        //this.pack();
	        
	        
		
	}
	
}
