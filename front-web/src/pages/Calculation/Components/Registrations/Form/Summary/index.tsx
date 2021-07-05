import { CostCalculation } from 'core/types/CostCalculation';
import Print from 'core/assets/images/print.png'
import { toast } from 'react-toastify';
import { makePrivateRequest } from 'core/utils/request';

import './styles.scss';


type Props = {
    costCalculation: CostCalculation;
}

const CostCalculationSummary = ({ costCalculation }: Props) => {
    const onPrint = () => {
        makePrivateRequest({ url: `/costcalculations/pdf?id=${costCalculation.id}`, responseType: "blob" })
            .then(response => {
                //Build a URL from the file
                var file = new Blob([response.data], { type: 'application/pdf' });
                const fileURL = URL.createObjectURL(file);
                //Open the URL on new Window
                window.open(fileURL);
            }).catch(() => {
                toast.error("Erro ao gerar relatório")
            })
    }

    return (
        <div>
            <div>
                <img
                    className="costCalculation-btn-print"
                    src={Print} alt=""
                    onClick={onPrint}
                />
            </div>
            <hr/>
            <h5>Periodo de Apuração:</h5>
            <div>
                <small>{costCalculation.startDate}</small>
                {` até `}
                <small>{costCalculation.endDate}</small>
            </div>
            <h5>Estoque Inicial:</h5>
            <div>
                <small>{costCalculation.stockStartDate}</small>
            </div>
            <h5>Status da Apuração:</h5>
            <div>
                <small>{costCalculation.status}</small>
            </div>
        </div>
    );
}
export default CostCalculationSummary;