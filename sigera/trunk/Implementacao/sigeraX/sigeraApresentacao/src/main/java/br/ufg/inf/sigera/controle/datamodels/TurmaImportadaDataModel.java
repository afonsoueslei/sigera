package br.ufg.inf.sigera.controle.datamodels;

import br.ufg.inf.sigera.modelo.TurmaImportada;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

public class TurmaImportadaDataModel extends ListDataModel<TurmaImportada> implements SelectableDataModel<TurmaImportada> {

    public TurmaImportadaDataModel(List<TurmaImportada> list) {
        super(list);
    }

    public TurmaImportadaDataModel() {
    }

    @Override
    public Object getRowKey(TurmaImportada t) {
        return t.getTurma().getId();
    }

    @Override
    public TurmaImportada getRowData(String numero) {
        List<TurmaImportada> tImportadas = (List<TurmaImportada>) getWrappedData();
        for (TurmaImportada dImportada : tImportadas) {
            if (dImportada.getTurma().getId() == Integer.parseInt(numero)) {
                return dImportada;
            }
        }
        return null;
    }
}
