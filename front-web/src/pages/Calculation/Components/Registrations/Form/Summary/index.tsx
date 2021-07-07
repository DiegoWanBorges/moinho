import './styles.scss';

import { CostCalculation } from 'core/types/CostCalculation';
import Print from 'core/assets/images/print.png'
import { toast } from 'react-toastify';
import { makePrivateRequest } from 'core/utils/request';
import Loader from "react-loader-spinner";
import { useState } from 'react';


type Props = {
    costCalculation: CostCalculation;
}

const CostCalculationSummary = ({ costCalculation }: Props) => {
    const [isLoading, setIsLoading] = useState(false);
    const onPrint = () => {
        setIsLoading(true)
        makePrivateRequest({ url: `/costcalculations/reports?id=${costCalculation.id}`, responseType: "blob" })
            .then(response => {
                var file = new Blob([response.data], { type: 'application/pdf' });
                const fileURL = URL.createObjectURL(file);
                window.open(fileURL);
            }).catch(() => {
                toast.error("Erro ao gerar relatório")
            })
            .finally(()=>setIsLoading(false))
    }

    return (
        <div>
            <div>
                {isLoading ? <Loader type="Rings" width={38} height={38} color="#0670B8" /> :(
                    <img
                    className="costCalculation-btn-print"
                    src={Print} alt=""
                    onClick={onPrint}
                />
                )}
                
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