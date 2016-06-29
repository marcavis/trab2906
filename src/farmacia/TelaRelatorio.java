package farmacia;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;

public class TelaRelatorio extends Composite {
	private Table table;
	private Cliente selecionado;
	private Cliente ultimoSelecionado;
	private DateTime dateTime;
	
	private ArrayList<Cliente> lista = new ArrayList<Cliente>();
	private ArrayList<Cliente> listaMostrada = new ArrayList<Cliente>();
	private Table tableRecomprar;
	private Label lblARenovar;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TelaRelatorio(Composite parent, int style) {
		super(parent, style);
		
		dateTime = new DateTime(this, SWT.BORDER);
		dateTime.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				preencheTabelaClientes();
				try {
					selecionado = listaMostrada.get(table.getSelectionIndex());
					preencheTabelaRecomprar(selecionado);
					} catch (ArrayIndexOutOfBoundsException aioobe) {
						//aioobe.printStackTrace();
						if(ultimoSelecionado != null)
							preencheTabelaRecomprar(ultimoSelecionado);
					}
			}
		});
		dateTime.setBounds(126, 10, 137, 54);
		
		Label lblData = new Label(this, SWT.NONE);
		lblData.setBounds(10, 29, 72, 19);
		lblData.setText("Data");
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 70, 137, 220);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				try {
					selecionado = listaMostrada.get(table.getSelectionIndex());
					preencheTabelaRecomprar(selecionado);
					ultimoSelecionado = selecionado;
					} catch (ArrayIndexOutOfBoundsException aioobe) {
						aioobe.printStackTrace();
					}
			}
		});
		
		TableColumn tblclmnNome = new TableColumn(table, SWT.NONE);
		tblclmnNome.setWidth(140);
		tblclmnNome.setText("Nome");
		
		tableRecomprar = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		tableRecomprar.setLinesVisible(true);
		tableRecomprar.setHeaderVisible(true);
		tableRecomprar.setBounds(173, 102, 267, 188);
		
		TableColumn tblclmnNomeMed = new TableColumn(tableRecomprar, SWT.NONE);
		tblclmnNomeMed.setWidth(100);
		tblclmnNomeMed.setText("Nome");
		
		TableColumn tblclmnValor = new TableColumn(tableRecomprar, SWT.NONE);
		tblclmnValor.setWidth(100);
		tblclmnValor.setText("Preço un.");
		
		lblARenovar = new Label(this, SWT.NONE);
		lblARenovar.setBounds(173, 70, 267, 26);
		lblARenovar.setText("Deve recomprar:");

	}
	
	private void preencheTabelaClientes() {
		table.setItemCount(0);
		tableRecomprar.setItemCount(0);
		Date dataEscolhida = new Date(dateTime.getYear()-1900,dateTime.getMonth(),dateTime.getDay());
		lista = Cliente.listar();
		listaMostrada = new ArrayList<Cliente>();
		for(Cliente cli : lista) {
			
			ArrayList<Compra> compras = Compra.listarPorCliente(cli);
			for (Compra compra : compras) {
				long duracaoDoEstoque = compra.getQtAdquirida()/compra.getDosagem();
				long tempoPercorrido = dataEscolhida.getTime() - compra.getData().getTime(); 
				long diasPercorridos = TimeUnit.DAYS.convert(tempoPercorrido, TimeUnit.MILLISECONDS); 
				if (diasPercorridos >= duracaoDoEstoque) {
					TableItem it = new TableItem(table, SWT.NONE);
					it.setText(cli.getNome());
					listaMostrada.add(cli);
					break;
				}
			}
			
		}
	}
	
	private void preencheTabelaRecomprar(Cliente cli) {
		System.out.println(cli.getNome());
		tableRecomprar.setItemCount(0);
		Date dataEscolhida = new Date(dateTime.getYear()-1900,dateTime.getMonth(),dateTime.getDay());
			
		ArrayList<Compra> compras = Compra.listarPorCliente(cli);
		for (Compra compra : compras) {
			long duracaoDoEstoque = compra.getQtAdquirida()/compra.getDosagem();
			long tempoPercorrido = dataEscolhida.getTime() - compra.getData().getTime(); 
			long diasPercorridos = TimeUnit.DAYS.convert(tempoPercorrido, TimeUnit.MILLISECONDS); 
			System.out.println(cli.getNome() + " - " + compra.getQtAdquirida() + "");
			System.out.println(duracaoDoEstoque + " - " + diasPercorridos);
			if (diasPercorridos >= duracaoDoEstoque) {
				TableItem it = new TableItem(tableRecomprar, SWT.NONE);
				it.setText(new String[] {compra.getMedicamento().getNome(), compra.getMedicamento().getValor()+""});
			}
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
