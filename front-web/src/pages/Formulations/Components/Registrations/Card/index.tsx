import './styles.scss';

import { Formulation } from 'core/types/Formulation';
import Print from 'core/assets/images/print.png'
import { toast } from 'react-toastify';
import { makePrivateRequest } from 'core/utils/request';
import Loader from "react-loader-spinner";
import history from 'core/utils/history';
import { useState } from 'react';


type Props = {
    formulation: Formulation;
    onRemove: (formulationId: number) => void;
}
const FormulationCard = ({ formulation, onRemove }: Props) => {
    const [isLoading, setIsLoading] = useState(false);

    const onEdit = () => {
        history.push(`/formulations/registrations/${formulation.id}`)
    }

    const onPrint = () => {
        setIsLoading(true);
        makePrivateRequest({ url: `/formulations/reports?id=${formulation.id}`, responseType: "blob" })
            .then(response => {
                var file = new Blob([response.data], { type: 'application/pdf' });
                const fileURL = URL.createObjectURL(file);
                window.open(fileURL);
            }).catch(() => {
                toast.error("Erro ao gerar relatório")
            })
            .finally(() => setIsLoading(false))
    }
    return (
        <div className="formulation-card">

            <div className="formulation-card-inf">
                <h5>{formulation.description}</h5>
            </div>

            <div className="formulation-card-action">
                
                {isLoading ? <Loader type="Rings" width={38} height={38} color="#0670B8" /> :
                    (<img
                        className="formulation-card-btn-print"
                        src={Print} alt=""
                        onClick={onPrint}
                    />)
                }

                <button
                    onClick={onEdit}
                    className="btn btn-outline-secondary formulation-card-action-btn formulation-card-action-btn-edit">
                    EDITAR
                </button>

                <button
                    type="button"
                    className="btn btn-outline-danger formulation-card-action-btn"
                    onClick={() => onRemove(formulation.id)}
                >
                    EXCLUIR
                </button>
            </div>
        </div>
    );
}
export default FormulationCard;
