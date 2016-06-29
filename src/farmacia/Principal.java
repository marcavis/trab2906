package farmacia;

import java.sql.Connection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import org.eclipse.swt.custom.CTabFolder;

public class Principal extends Shell {
	private CTabItem tabClientes;
	private CTabItem tabMedicamentos;
	private CTabItem tabCompras;
	private CTabItem tabRelatorios;
	//private CTabItem tabBackup;
	
	private Composite compClientes;
	private Composite compMedicamentos;
	private Composite compCompras;
	private Composite compRelatorios;
	//private Composite compBackup;
	
	private CTabFolder tabFolder;

	public static Connection conn = Conexao.conn();
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			Principal shell = new Principal(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public Principal(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		tabFolder = new CTabFolder(this, SWT.BORDER);
		tabFolder.setBounds(10, 10, 612, 562);
		tabFolder.setSimple(false);
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		
		Menu menu = new Menu(this, SWT.BAR);
		setMenuBar(menu);
		
		MenuItem mntmcadastro = new MenuItem(menu, SWT.CASCADE);
		mntmcadastro.setText("&Cadastro");
		
		Menu menu_1 = new Menu(mntmcadastro);
		mntmcadastro.setMenu(menu_1);
		
		MenuItem mntmclientes = new MenuItem(menu_1, SWT.NONE);
		mntmclientes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!verificaAba("Clientes")) { 
					tabClientes = new CTabItem(tabFolder, SWT.NONE);
					tabClientes.setShowClose(true);
					tabClientes.setText("Clientes");
				
					compClientes = new TelaCliente(tabFolder, SWT.NONE);
					tabClientes.setControl(compClientes);
					tabFolder.setSelection(tabClientes);
				}
			}
		});
		mntmclientes.setText("&Clientes");
		
		MenuItem mntmmedicamentos = new MenuItem(menu_1, SWT.NONE);
		mntmmedicamentos.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!verificaAba("Medicamentos")) { 
					tabMedicamentos = new CTabItem(tabFolder, SWT.NONE);
					tabMedicamentos.setShowClose(true);
					tabMedicamentos.setText("Medicamentos");
				
					compMedicamentos = new TelaMedicamento(tabFolder, SWT.NONE);
					tabMedicamentos.setControl(compMedicamentos);
					tabFolder.setSelection(tabMedicamentos);
				}
			}
		});
		mntmmedicamentos.setText("&Medicamentos");
		
		new MenuItem(menu_1, SWT.SEPARATOR);
		
		MenuItem mntmcompras = new MenuItem(menu_1, SWT.NONE);
		mntmcompras.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!verificaAba("Compras")) { 
					tabCompras = new CTabItem(tabFolder, SWT.NONE);
					tabCompras.setShowClose(true);
					tabCompras.setText("Compras");
				
					compCompras = new TelaCompra(tabFolder, SWT.NONE);
					tabCompras.setControl(compCompras);
					tabFolder.setSelection(tabCompras);
				}
			}
		});
		mntmcompras.setText("C&ompras");
		
		new MenuItem(menu_1, SWT.SEPARATOR);
		
		MenuItem mntmrelatorio = new MenuItem(menu_1, SWT.NONE);
		mntmrelatorio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(!verificaAba("Relatórios")) { 
					tabRelatorios = new CTabItem(tabFolder, SWT.NONE);
					tabRelatorios.setShowClose(true);
					tabRelatorios.setText("Relatórios");
				
					compRelatorios = new TelaRelatorio(tabFolder, SWT.NONE);
					tabRelatorios.setControl(compRelatorios);
					tabFolder.setSelection(tabRelatorios);
				}
			}
		});
		mntmrelatorio.setText("&Relatórios");
		
		new MenuItem(menu_1, SWT.SEPARATOR);
		
		MenuItem mntmsair = new MenuItem(menu_1, SWT.NONE);
		mntmsair.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		mntmsair.setText("&Sair");
		
		
		
		createContents();
	}

	private boolean verificaAba(String nome){
		CTabItem[] items = tabFolder.getItems();
		int indice = -1;
		for(int i = 0;i<items.length;i++){
			if(items[i].getText().equals(nome)){
				indice = i;
				break;
			}
		}
		if(indice != -1){
			tabFolder.setSelection(indice);
			return true;
		}
		return false;
			
	}
	
	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(638, 640);
		setLocation(500, 200);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
