package farmacia;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Text;

import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class TelaCliente extends Composite {
	private DateTime dateTime;
	private Button btnMasc;
	private Button btnFem;
	private Text textNome;
	private Color white;
	
	private Button btnIncluir;
	private Button btnAlterar;
	private Button btnExcluir;
	private Table table;
	
	private Cliente selecionado;
	
	private ArrayList<Cliente> lista = new ArrayList<Cliente>();

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public TelaCliente(Composite parent, int style) {
		super(parent, style);
		
		Label lblNome = new Label(this, SWT.NONE);
		lblNome.setBounds(10, 14, 97, 19);
		lblNome.setText("Nome");
		
		textNome = new Text(this, SWT.BORDER);
		textNome.setBounds(113, 10, 181, 23);
		white = textNome.getBackground();
		
		Label labelNasc = new Label(this, SWT.NONE);
		labelNasc.setText("Nascimento");
		labelNasc.setBounds(10, 47, 97, 19);
		
		dateTime = new DateTime(this, SWT.BORDER | SWT.LONG);
		dateTime.setBounds(113, 39, 181, 54);
		
		//Calendar cal = Calendar.getInstance();
		//cal.setTime(date);
		//cal.add(Calendar.MINUTE, 5);
		//Date newDate = cal.getTime();
		
		Group grpSexo = new Group(this, SWT.NONE);
		grpSexo.setText("Sexo");
		grpSexo.setBounds(10, 99, 284, 46);
		
		btnMasc = new Button(grpSexo, SWT.RADIO);
		btnMasc.setBounds(10, 0, 114, 24);
		btnMasc.setText("Masculino");
		btnMasc.setSelection(true);
		
		btnFem = new Button(grpSexo, SWT.RADIO);
		btnFem.setText("Feminino");
		btnFem.setBounds(154, 0, 114, 24);
		
		btnIncluir = new Button(this, SWT.NONE);
		btnIncluir.setBounds(10, 151, 94, 33);
		btnIncluir.setText("Incluir");
		btnIncluir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					getTela().cadastrar();
				} catch (NullPointerException ef) {
					ef.printStackTrace();
				}
				
				preencheTabela(null);
			}
		});
		
		btnAlterar = new Button(this, SWT.NONE);
		btnAlterar.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					Cliente novo = getTela();
					novo.setId(selecionado.getId());
					novo.alterar();
				} catch (NullPointerException ef) {
					ef.printStackTrace();
				}
				preencheTabela(null);
			}
		});
		btnAlterar.setText("Alterar");
		btnAlterar.setBounds(110, 151, 94, 33);
		
		btnExcluir = new Button(this, SWT.NONE);
		btnExcluir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selecionado.excluir();
				preencheTabela(null);
			}
		});
		btnExcluir.setText("Excluir");
		btnExcluir.setBounds(210, 151, 94, 33);
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 190, 560, 281);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				try {
					selecionado = lista.get(table.getSelectionIndex());
					setTela(selecionado);
				} catch (ArrayIndexOutOfBoundsException aioobe) {
					aioobe.printStackTrace();
				}
			}
		});
		
		
		
		TableColumn tblclmnId = new TableColumn(table, SWT.NONE);
		tblclmnId.setWidth(100);
		tblclmnId.setText("ID");
		
		TableColumn tblclmnNome = new TableColumn(table, SWT.NONE);
		tblclmnNome.setWidth(140);
		tblclmnNome.setText("Nome");
		
		TableColumn tblclmnNascimento = new TableColumn(table, SWT.NONE);
		tblclmnNascimento.setWidth(140);
		tblclmnNascimento.setText("Nascimento");
		
		TableColumn tblclmnSexo = new TableColumn(table, SWT.NONE);
		tblclmnSexo.setWidth(50);
		tblclmnSexo.setText("Sexo");

		preencheTabela(null);
		
//		Calendar cal = Calendar.getInstance();
//		cal.setTime(lista.get(0).getNascimento());
//		cal.add(Calendar.DATE, 7);
//		System.out.println(cal.getTime());
	}

	private void preencheTabela(String filtro) {
		table.setItemCount(0);
		lista = Cliente.listar();
		for(Cliente c : lista) {
			TableItem it = new TableItem(table, SWT.NONE);
			it.setText(c.toArray());
		}
	}
	
	private Cliente getTela() {
		if (textNome.getText().length() >  0) {
			Cliente c = new Cliente();
			c.setNome(textNome.getText());
			Date nasc = new Date(dateTime.getYear()-1900,dateTime.getMonth(),dateTime.getDay());
			c.setNascimento(nasc);
			c.setSexo(btnMasc.getSelection()?"M":"F");
			textNome.setBackground(white);
			return c;
		} else {
			Device device = Display.getCurrent();
			Color red = new Color(device, 255,0,0);
			textNome.setBackground(red);
			return null;
		}
	}
	
	private void setTela(Cliente c) {
		textNome.setText(c.getNome());
		dateTime.setDate(c.getNascimento().getYear(),c.getNascimento().getMonth(), c.getNascimento().getDate());
		if (c.getSexo().equals("M")) {
			btnMasc.setSelection(true);
			btnFem.setSelection(false);
		}
		else {
			btnMasc.setSelection(false);
			btnFem.setSelection(true);
		}
	}
	
	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
