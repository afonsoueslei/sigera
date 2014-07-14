package br.ufg.inf.sigera.controle;

import br.ufg.inf.sigera.controle.tela.DisciplinaTela;
import br.ufg.inf.sigera.modelo.Disciplina;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author auf
 */
public class DisciplinaBeanTest {
    
    public DisciplinaBeanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Teste do método getDisciplinasTela, da classe DisciplinaBean.
     */
    @Test
    public void testGetDisciplinasTela() {
        System.out.println("getDisciplinasTela");
        DisciplinaBean instance = new DisciplinaBean();
        List expResult = null;
        List result = instance.getDisciplinasTela();
        assertEquals(expResult, result);        
    }

    /**
     * Teste do método setDisciplinasTela, da classe DisciplinaBean.
     */
    @Test
    public void testSetDisciplinasTela() {
        System.out.println("setDisciplinasTela");
        List<DisciplinaTela> disciplinasTela = null;
        DisciplinaBean instance = new DisciplinaBean();
        instance.setDisciplinasTela(disciplinasTela);
    }

    /**
     * Teste do método getDisciplinaExcluir, da classe DisciplinaBean.
     */
    @Test
    public void testGetDisciplinaExcluir() {
        System.out.println("getDisciplinaExcluir");
        DisciplinaBean instance = new DisciplinaBean();
        Disciplina expResult = null;
        Disciplina result = instance.getDisciplinaExcluir();
        assertEquals(expResult, result);
    }
//
//    /**
//     * Test of setDisciplinaExcluir method, of class DisciplinaBean.
//     */
//    @Test
//    public void testSetDisciplinaExcluir() {
//        System.out.println("setDisciplinaExcluir");
//        Disciplina disciplinaExcluir = null;
//        DisciplinaBean instance = new DisciplinaBean();
//        instance.setDisciplinaExcluir(disciplinaExcluir);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getCodigoCurso method, of class DisciplinaBean.
//     */
//    @Test
//    public void testGetCodigoCurso() {
//        System.out.println("getCodigoCurso");
//        DisciplinaBean instance = new DisciplinaBean();
//        Integer expResult = null;
//        Integer result = instance.getCodigoCurso();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setCodigoCurso method, of class DisciplinaBean.
//     */
//    @Test
//    public void testSetCodigoCurso() {
//        System.out.println("setCodigoCurso");
//        Integer codigoCurso = null;
//        DisciplinaBean instance = new DisciplinaBean();
//        instance.setCodigoCurso(codigoCurso);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getListaCursos method, of class DisciplinaBean.
//     */
//    @Test
//    public void testGetListaCursos() {
//        System.out.println("getListaCursos");
//        DisciplinaBean instance = new DisciplinaBean();
//        List expResult = null;
//        List result = instance.getListaCursos();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setDisciplinaSelecionada method, of class DisciplinaBean.
//     */
//    @Test
//    public void testSetDisciplinaSelecionada() {
//        System.out.println("setDisciplinaSelecionada");
//        Disciplina disciplinaSelecionada = null;
//        DisciplinaBean instance = new DisciplinaBean();
//        instance.setDisciplinaSelecionada(disciplinaSelecionada);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDisciplinaEditavel method, of class DisciplinaBean.
//     */
//    @Test
//    public void testGetDisciplinaEditavel() {
//        System.out.println("getDisciplinaEditavel");
//        DisciplinaBean instance = new DisciplinaBean();
//        Disciplina expResult = null;
//        Disciplina result = instance.getDisciplinaEditavel();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setDisciplinaEditavel method, of class DisciplinaBean.
//     */
//    @Test
//    public void testSetDisciplinaEditavel() {
//        System.out.println("setDisciplinaEditavel");
//        Disciplina disciplinaEditavel = null;
//        DisciplinaBean instance = new DisciplinaBean();
//        instance.setDisciplinaEditavel(disciplinaEditavel);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of salvar method, of class DisciplinaBean.
//     */
//    @Test
//    public void testSalvar() {
//        System.out.println("salvar");
//        DisciplinaBean instance = new DisciplinaBean();
//        String expResult = "";
//        String result = instance.salvar();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of remover method, of class DisciplinaBean.
//     */
//    @Test
//    public void testRemover() {
//        System.out.println("remover");
//        DisciplinaBean instance = new DisciplinaBean();
//        instance.remover();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDataModel method, of class DisciplinaBean.
//     */
//    @Test
//    public void testGetDataModel() {
//        System.out.println("getDataModel");
//        DisciplinaBean instance = new DisciplinaBean();
//        DisciplinaDataModel expResult = null;
//        DisciplinaDataModel result = instance.getDataModel();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDisciplinasFiltradas method, of class DisciplinaBean.
//     */
//    @Test
//    public void testGetDisciplinasFiltradas() {
//        System.out.println("getDisciplinasFiltradas");
//        DisciplinaBean instance = new DisciplinaBean();
//        List expResult = null;
//        List result = instance.getDisciplinasFiltradas();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of setDisciplinasFiltradas method, of class DisciplinaBean.
//     */
//    @Test
//    public void testSetDisciplinasFiltradas() {
//        System.out.println("setDisciplinasFiltradas");
//        List<DisciplinaTela> disciplinasFiltrados = null;
//        DisciplinaBean instance = new DisciplinaBean();
//        instance.setDisciplinasFiltradas(disciplinasFiltrados);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of editar method, of class DisciplinaBean.
//     */
//    @Test
//    public void testEditar() {
//        System.out.println("editar");
//        DisciplinaBean instance = new DisciplinaBean();
//        String expResult = "";
//        String result = instance.editar();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of cancelar method, of class DisciplinaBean.
//     */
//    @Test
//    public void testCancelar() {
//        System.out.println("cancelar");
//        DisciplinaBean instance = new DisciplinaBean();
//        instance.cancelar();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of voltarListaDisciplinas method, of class DisciplinaBean.
//     */
//    @Test
//    public void testVoltarListaDisciplinas() {
//        System.out.println("voltarListaDisciplinas");
//        DisciplinaBean instance = new DisciplinaBean();
//        String expResult = "";
//        String result = instance.voltarListaDisciplinas();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of preProcess method, of class DisciplinaBean.
//     */
//    @Test
//    public void testPreProcess() {
//        System.out.println("preProcess");
//        Object document = null;
//        DisciplinaBean instance = new DisciplinaBean();
//        instance.preProcess(document);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}