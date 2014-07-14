package br.ufg.inf.sigera.controle.datamodels;

import br.ufg.inf.sigera.modelo.DisciplinaImportada;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

public class DisciplinaImportadaDataModel extends ListDataModel<DisciplinaImportada> implements SelectableDataModel<DisciplinaImportada> {

    public DisciplinaImportadaDataModel(List<DisciplinaImportada> list) {
        super(list);
    }

    public DisciplinaImportadaDataModel() {
    }

    @Override
    public Object getRowKey(DisciplinaImportada t) {
        return t.getDisciplina().getId();
    }

    @Override
    public DisciplinaImportada getRowData(String numero) {
        List<DisciplinaImportada> disciplinasImportadas = (List<DisciplinaImportada>) getWrappedData();
        for (DisciplinaImportada dImportada : disciplinasImportadas) {
            if (dImportada.getDisciplina().getId() == Integer.parseInt(numero)) {
                return dImportada;
            }
        }
        return null;
    }
}
