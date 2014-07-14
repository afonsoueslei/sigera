package br.ufg.inf.sigera.controle.datamodels;

import br.ufg.inf.sigera.controle.tela.DisciplinaTela;
import java.util.List;
import javax.faces.model.ListDataModel;
import org.primefaces.model.SelectableDataModel;

public class DisciplinaDataModel extends ListDataModel<DisciplinaTela> implements SelectableDataModel<DisciplinaTela> {

    public DisciplinaDataModel() {
    }

    public DisciplinaDataModel(List<DisciplinaTela> list) {
        super(list);
    }

    @Override
    public Object getRowKey(DisciplinaTela p) {
        return p.getNumero();
    }

    @Override
    public DisciplinaTela getRowData(String numero) {
        List<DisciplinaTela> disciplinas = (List<DisciplinaTela>) getWrappedData();
        for (DisciplinaTela disciplina : disciplinas) {
            if (disciplina.getNumero() == Integer.parseInt(numero)) {
                return disciplina;
            }
        }
        return null;
    }
}
