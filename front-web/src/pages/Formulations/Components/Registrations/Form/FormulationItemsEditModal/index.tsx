import { FormulationItem } from 'core/types/Formulation';
import './styles.scss';
import Modal from 'react-modal';
import { useState } from 'react';
import { useForm } from 'react-hook-form';

type Props = {
    formulationItems: FormulationItem;
    onEditItem: (formulationItem: FormulationItem) => void;
}
function FormulationItemEditModal({ formulationItems, onEditItem }: Props) {
    const { register, errors, handleSubmit,setValue } = useForm<FormulationItem>();
    const [show, setShow] = useState(false);

    const onEdit = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        e.preventDefault();
        setShow(!show)
    }
    const customStyles = {
        content: {
            top: '50%',
            left: '50%',
            right: 'auto',
            bottom: 'auto',
            marginRight: '-50%',
            transform: 'translate(-50%, -50%)'
        }
    };
    function afterOpenModal() {
        setValue('quantity',formulationItems.quantity);
        setValue('round',formulationItems.round.toString());
        setValue('rawMaterial',formulationItems.rawMaterial.toString());
        setValue('type',formulationItems.type);  
    }
    const onSave = (data:FormulationItem) => {
        const payload={
            ...data,
            product:formulationItems.product
        }
        onEditItem(payload);
        setShow(!show);
    }


    return (
        <>
            <button
                className="btn btn-primary btn-sm formulation-item-card-actions-btn-edit"
                onClick={e => onEdit(e)}
            >
                Editar
            </button>
            <Modal
                isOpen={show}
                style={customStyles}
                onAfterOpen={afterOpenModal}
            >
                <h5>{formulationItems.product.name}</h5>
                
                <div className="formulationItemEdit-row">
                <div className="formulationItemEdit-quantity">
                    <label className="label-base">Quantidade:</label>
                    <input
                        className="input-base"
                        name="quantity"
                        type="number"
                        ref={register({
                            required: "Campo obrigatório",
                            min: { value: 0.001, message: "O valor dever ser maior que zero" },
                        })}
                    />
                    {errors.quantity && (
                        <div className="invalid-feedback d-block">
                            {errors.quantity.message}
                        </div>
                    )}
                </div>

                <div className="formulationItemEdit-round">
                    <label className="label-base">Arredondar? </label>
                    <div className="parameter-content-withou-stock-">
                        <input
                            ref={register}
                            type="radio"
                            value="true"
                            name="round"
                        /> Sim
                    <input
                            ref={register}
                            type="radio"
                            value="false"
                            name="round"
                        /> Não
               </div>
                </div>

                <div className="formulationItemEdit-raw-material">
                    <label className="label-base">Materia-prima? </label>
                    <div className="parameter-content-withou-stock-">
                        <input
                            ref={register}
                            type="radio"
                            value="true"
                            name="rawMaterial"
                        /> Sim
                    <input
                            ref={register}
                            type="radio"
                            value="false"
                            name="rawMaterial"
                        /> Não
               </div>
                </div>

                <div className="formulationItemEdit-type">
                    <label className="label-base">Tipo? </label>
                    <select name="type" ref={register} className="parameter-content-type-cost-select">
                        <option value="NORMAL">Normal</option>
                        <option value="EXTRA">Extra</option>
                    </select>
                </div>
                </div>




                <div className="formulationItemEdit-btn">
                    <button
                        className="btn btn-primary formulationItemEdit-btn-save"
                        onClick={handleSubmit(onSave)}                    >
                        Salvar
                    </button>

                    <button
                        className="btn btn-danger formulationItemEdit-btn-del"
                        onClick={() => setShow(!show)}
                    >
                        Cancelar
                    </button>
                </div>

            </Modal>
        </>
    );
}
export default FormulationItemEditModal;
