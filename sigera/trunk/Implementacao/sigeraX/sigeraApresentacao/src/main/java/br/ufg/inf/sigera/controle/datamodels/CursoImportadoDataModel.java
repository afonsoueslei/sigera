package br.ufg.inf.sigera.controle.datamodels;

import br.ufg.inf.sigera.modelo.CursoImportado;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

public class CursoImportadoDataModel extends ListDataModel<CursoImportado> implements SelectableDataModel<CursoImportado> {

    public CursoImportadoDataModel(List<CursoImportado> list) {
        super(list);
    }

    public CursoImportadoDataModel() {
    }

    @Override
    public Object getRowKey(CursoImportado t) {
        return t.getCurso().getId();
    }

    @Override
    public CursoImportado getRowData(String numero) {
        List<CursoImportado> cursosImportados = (List<CursoImportado>) getWrappedData();
        for (CursoImportado cImportado : cursosImportados) {
            if (cImportado.getCurso().getId() == Integer.parseInt(numero)) {
                return cImportado;
            }
        }
        return null;
    }
}
