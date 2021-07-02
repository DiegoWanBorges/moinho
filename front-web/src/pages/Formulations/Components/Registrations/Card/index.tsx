import { Formulation } from 'core/types/Formulation';
import { Link } from 'react-router-dom';
import Print from 'core/assets/images/print.png'
import './styles.scss';
import { makePrivateRequest } from 'core/utils/request';
import { toast } from 'react-toastify';

type Props={
    formulation:Formulation;
    onRemove: (formulationId:number) =>void;
}
const FormulationCard = ({ formulation,onRemove }:Props) => {

    const onPrint = () => {
        makePrivateRequest({ url: `/formulations?pdf=${formulation.id}`, responseType: "blob" })
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
        <div className="formulation-card">
         
          <div className="formulation-card-inf">
            <h5>{formulation.description}</h5>
          </div>

          <div className="formulation-card-action">
                <img  
                    className="formulation-card-btn-print" 
                    src={Print} alt="" 
                    onClick={onPrint}
                />

                <Link
                    to={`/formulations/registrations/${formulation.id}`}
                    type="button"
                    className="btn btn-outline-secondary formulation-card-action-btn formulation-card-action-btn-edit">
                    EDITAR
                </Link>

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
