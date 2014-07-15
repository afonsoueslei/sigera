package br.ufg.inf.sigera.controle.datamodels;

import br.ufg.inf.sigera.modelo.ProfessorImportado;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

public class ProfessorImportadoDataModel extends ListDataModel<ProfessorImportado> implements SelectableDataModel<ProfessorImportado> {

    public ProfessorImportadoDataModel(List<ProfessorImportado> list) {
        super(list);
    }

    @Override
    public Object getRowKey(ProfessorImportado t) {
         return t.getProfessor().getId();
    }

    @Override
    public ProfessorImportado getRowData(String numero) {
      List<ProfessorImportado> disciplinasImportadas = (List<ProfessorImportado>) getWrappedData();
        for (ProfessorImportado dImportada : disciplinasImportadas) {
            if (dImportada.getProfessor().getId() == Integer.parseInt(numero)) {
                return dImportada;
            }
        }
        return null;  
    }
}
