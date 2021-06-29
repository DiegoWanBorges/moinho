import { Formulation } from "core/types/Formulation";
import { jsPDF } from "jspdf";

type Items ={
    Codigo:string;
    name:string;
    qt:string,
    un:string,
    type:string
}

export const FormulationReport = (formulation?: Formulation) => {
    const doc = new jsPDF({ orientation: "portrait" });
    doc.setLanguage("pt-BR")
    var width = doc.internal.pageSize.getWidth()

    doc.setFontSize(20);
    doc.setTextColor("#0670B8")
    doc.text('Moinho', width / 2, 10, { align: 'center' });
    doc.setFontSize(12);
    doc.text('Controle de produção e apuração de custo', width / 2, 15, { align: 'center' })

    doc.setTextColor("#000")
    doc.setFontSize(14);
    doc.text(`Formulação: ${formulation?.description}`, 10, 23)
    doc.text(`Quantidade: ${formulation?.coefficient} ${formulation?.product.unity.id}`, 10, 30)

    doc.line(5, 35, width-5 , 35);

    doc.text('Centro de custo para rateio', width / 2, 45, { align: 'center' })
    doc.line(100, 47, 100, 100);
    doc.text('Mão de Obra', 40,53)
    doc.text('Operacionais', 130,53)

    var y = 55;
    doc.setFontSize(8);
    formulation?.sectors.forEach(item => {
        y+=5;
        doc.text(item.name, 10,y)
    });
    y=55;
    formulation?.operationalCostType.forEach(item => {
        y+=5;
        doc.text(item.name, 110,y)
    });
    
    doc.line(5, 100, width-5 , 100);
    doc.setFontSize(14);
    doc.text('Ingredientes', width / 2, 110, { align: 'center' })

    
    const data :Items[]=[];
    formulation?.formulationItems.map(item =>(
        data.push({
            Codigo:  item.product.id.toString(),
            name: item.product.name,
            qt:item.quantity.toString(),
            un:item.product.unity.id,
            type:item.type
        })
    )) 
      
      var headers = ([
        "Codigo",
        "name",
        "qt",
        "un",
        "type",
      ]);
      
      doc.table(10, 115, data, headers, { autoSize: false,headerBackgroundColor:"#FFF"});
      





    doc.save(`Formulação - ${formulation?.id}`);
}

