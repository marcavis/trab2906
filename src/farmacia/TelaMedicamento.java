package farmacia;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import java.util.ArrayList;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class TelaMedicamento extends Composite {
	private Text textNome;
	private Text textQuant;
	private Text textValor;
	private Table table;
	
	private Button btnIncluir;
	private Button btnAlterar;
	private Button btnExcluir;
	
	private Medicamento selecionado;
	
	private ArrayList<Medicamento> lista = new ArrayList<Medicamento>();
	
	private Device device = Display.getCurrent();
	private Color red = new Color(device, 255,0,0);
	private Color white;
	private Button btnComp;
	private Button btnLiquido;
	private Button btnGotas;
	
	public TelaMedicamento(Composite parent, int style) {
		super(parent, style);
		
		Label lblNome = new Label(this, SWT.NONE);
		lblNome.setBounds(10, 14, 72, 19);
		lblNome.setText("Nome");
		
		textNome = new Text(this, SWT.BORDER);
		textNome.setBounds(155, 10, 159, 23);
		white = textNome.getBackground();
		
		textQuant = new Text(this, SWT.BORDER);
		textQuant.setBounds(155, 39, 159, 23);
		
		Label lblTipo = new Label(this, SWT.NONE);
		lblTipo.setBounds(320, 39, 120, 19);
		lblTipo.setText("comprimidos");
		
		Group grpApresentao = new Group(this, SWT.NONE);
		grpApresentao.setText("Apresentação");
		grpApresentao.setBounds(10, 68, 430, 46);
		
		btnComp = new Button(grpApresentao, SWT.RADIO);
		btnComp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblTipo.setText("comprimidos");
			}
		});
		btnComp.setBounds(10, 0, 114, 24);
		btnComp.setText("comprimidos");
		btnComp.setSelection(true);
		
		btnLiquido = new Button(grpApresentao, SWT.RADIO);
		btnLiquido.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblTipo.setText("ml");
			}
		});
		btnLiquido.setText("líquido");
		btnLiquido.setBounds(130, 0, 114, 24);
		
		btnGotas = new Button(grpApresentao, SWT.RADIO);
		btnGotas.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				lblTipo.setText("gotas");
			}
		});
		btnGotas.setText("gotas");
		btnGotas.setBounds(250, 0, 114, 24);
		
		Label lblQuantidade = new Label(this, SWT.NONE);
		lblQuantidade.setText("Quantidade");
		lblQuantidade.setBounds(10, 43, 139, 19);
		
		Label lblValor = new Label(this, SWT.NONE);
		lblValor.setText("Valor");
		lblValor.setBounds(10, 124, 72, 19);
		
		textValor = new Text(this, SWT.BORDER);
		textValor.setBounds(155, 120, 159, 23);
		
		btnIncluir = new Button(this, SWT.NONE);
		btnIncluir.setBounds(10, 149, 94, 33);
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
					Medicamento novo = getTela();
					novo.setId(selecionado.getId());
					novo.alterar();
				} catch (NullPointerException ef) {
					ef.printStackTrace();
				}
				preencheTabela(null);
			}
		});
		btnAlterar.setText("Alterar");
		btnAlterar.setBounds(110, 149, 94, 33);
		
		btnExcluir = new Button(this, SWT.NONE);
		btnExcluir.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				selecionado.excluir();
				preencheTabela(null);
			}
		});
		btnExcluir.setText("Excluir");
		btnExcluir.setBounds(210, 149, 94, 33);
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 188, 560, 281);
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
		
		TableColumn tblclmnApresentacao = new TableColumn(table, SWT.NONE);
		tblclmnApresentacao.setWidth(70);
		tblclmnApresentacao.setText("Apres.");
		
		TableColumn tblclmnQuantidade = new TableColumn(table, SWT.NONE);
		tblclmnQuantidade.setWidth(90);
		tblclmnQuantidade.setText("Quantidade");
		
		TableColumn tblclmnValor =  new TableColumn(table, SWT.NONE);
		tblclmnValor.setWidth(120);
		tblclmnValor.setText("Valor");
		
		preencheTabela(null);
		
	}
	
	private void preencheTabela(String filtro) {
		table.setItemCount(0);
		lista = Medicamento.listar(filtro);
		for(Medicamento m : lista) {
			TableItem it = new TableItem(table, SWT.NONE);
			it.setText(m.toArray());
		}
	}
	
	private Medicamento getTela() {
		textNome.setBackground(white);
		textQuant.setBackground(white);
		textValor.setBackground(white);
		int quant, valor;
		try {
			quant = Integer.parseInt(textQuant.getText());
			if(quant <= 0)
				throw new Exception();
		} catch (Exception nfe) {
			textQuant.setBackground(red);
			return null;
		}
		try {
			valor = Integer.parseInt(textValor.getText());
			if(valor <= 0)
				throw new Exception();
		} catch (Exception nfe) {
			textValor.setBackground(red);
			return null;
		}
		if (textNome.getText().length() >  0) {
			Medicamento m = new Medicamento();
			m.setNome(textNome.getText());
			String apresentacao = "";
			if(btnComp.getSelection())
				apresentacao = "comprimidos";
			else if (btnLiquido.getSelection())
				apresentacao = "liquido";
			else
				apresentacao = "gotas";
			m.setApresentacao(apresentacao);
			m.setQuantidade(quant);
			m.setValor(valor);

			return m;
		} else {
			
			textNome.setBackground(red);
			return null;
		}
	}
	
	private void setTela(Medicamento m) {
		textNome.setText(m.getNome());
		if (m.getApresentacao().equals("comprimidos")) {
			btnComp.setSelection(true);
			btnLiquido.setSelection(false);
			btnGotas.setSelection(false);
		}
		
		else if (m.getApresentacao().equals("liquido")) {
			btnComp.setSelection(false);
			btnLiquido.setSelection(true);
			btnGotas.setSelection(false);
		}
		else {
			btnComp.setSelection(false);
			btnLiquido.setSelection(false);
			btnGotas.setSelection(true);
		}
			
		textQuant.setText(m.getQuantidade()+"");
		textValor.setText(m.getValor()+"");
	}
	
}
