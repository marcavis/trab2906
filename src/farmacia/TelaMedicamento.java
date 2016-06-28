package farmacia;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

public class TelaMedicamento extends Composite {
	private Text text;
	private Text text_1;
	private Text textValor;
	private Table table;
	
	private Button btnIncluir;
	
	public TelaMedicamento(Composite parent, int style) {
		super(parent, style);
		
		Label lblNome = new Label(this, SWT.NONE);
		lblNome.setBounds(10, 14, 72, 19);
		lblNome.setText("Nome");
		
		text = new Text(this, SWT.BORDER);
		text.setBounds(155, 10, 159, 23);
		
		text_1 = new Text(this, SWT.BORDER);
		text_1.setBounds(155, 39, 159, 23);
		
		Label lblTipo = new Label(this, SWT.NONE);
		lblTipo.setBounds(320, 39, 120, 19);
		lblTipo.setText("comprimidos");
		
		Group grpApresentao = new Group(this, SWT.NONE);
		grpApresentao.setText("Apresentação");
		grpApresentao.setBounds(10, 68, 430, 46);
		
		Button btnComp = new Button(grpApresentao, SWT.RADIO);
		btnComp.setBounds(10, 0, 114, 24);
		btnComp.setText("comprimidos");
		btnComp.setSelection(true);
		
		Button btnLiquido = new Button(grpApresentao, SWT.RADIO);
		btnLiquido.setText("líquido");
		btnLiquido.setBounds(130, 0, 114, 24);
		
		Button btnGotas = new Button(grpApresentao, SWT.RADIO);
		btnGotas.setText("gotas");
		btnGotas.setBounds(250, 0, 114, 24);
		
		Label lblQuantidade = new Label(this, SWT.NONE);
		lblQuantidade.setText("Quantidade");
		lblQuantidade.setBounds(10, 43, 139, 19);
		
		Label lblValor = new Label(this, SWT.NONE);
		lblValor.setText("Nome");
		lblValor.setBounds(10, 124, 72, 19);
		
		textValor = new Text(this, SWT.BORDER);
		textValor.setBounds(155, 120, 159, 23);
		
		btnIncluir = new Button(this, SWT.NONE);
		btnIncluir.setBounds(10, 149, 94, 33);
		btnIncluir.setText("Incluir");
		
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 188, 560, 281);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
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
		
	}
}
