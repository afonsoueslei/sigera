package br.ufg.inf.sigera.controle.datamodels;

import br.ufg.inf.sigera.controle.tela.CursoTelaManter;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

public class CursoDataModel extends ListDataModel<CursoTelaManter> implements SelectableDataModel<CursoTelaManter> {

    public CursoDataModel(List<CursoTelaManter> list) {
        super(list);
    }

    public CursoDataModel() {
    }

    @Override
    public Object getRowKey(CursoTelaManter t) {
        return t.getNumero();
    }

    @Override
    public CursoTelaManter getRowData(String numero) {
        List<CursoTelaManter> cursos = (List<CursoTelaManter>) getWrappedData();
        for (CursoTelaManter curso : cursos) {
            if (curso.getNumero()== Integer.parseInt(numero)) {
                return curso;
            }
        }
        return null;
    }
}
