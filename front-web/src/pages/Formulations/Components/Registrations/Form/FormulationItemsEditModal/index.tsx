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
    const { register, errors, handleSubmit, setValue } = useForm<FormulationItem>();
    const [show, setShow] = useState(false);

    const onEdit = (e: React.MouseEvent<HTMLButtonElement, MouseEvent>) => {
        e.preventDefault();
        setShow(!show)
    }

    function afterOpenModal() {
        setValue('quantity', formulationItems.quantity);
        setValue('round', formulationItems.round.toString());
        setValue('rawMaterial', formulationItems.rawMaterial.toString());
        setValue('type', formulationItems.type);
    }
    const onSave = (data: FormulationItem) => {
        const payload = {
            ...data,
            product: formulationItems.product
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
                className="formulationModalEdit-main"
                overlayClassName="formulationModalEdit-overlay"
                onAfterOpen={afterOpenModal}
            >
                <h5 className="formulationModalEdit-product">{formulationItems.product.name}</h5>

                <div className="formulationModalEdit-row">
                    <div className="formulationModalEdit-quantity">
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

                    <div className="formulationModalEdit-round">
                        <label className="label-base">Arredondar? </label>
                        <div className="formulationModalEdit-round-item">
                            <input
                                ref={register}
                                type="radio"
                                value="true"
                                name="round"

                            />
                            <label className="formulationModalEdit-select-item-yes">Sim</label>
                            <input
                                ref={register}
                                type="radio"
                                value="false"
                                name="round"
                            /> Não
                         </div>
                    </div>

                    <div className="formulationModalEdit-raw-material">
                        <label className="label-base">Materia-prima? </label>
                        <div className="formulationModalEdit-rawMaterial-item">
                            <input
                                ref={register}
                                type="radio"
                                value="true"
                                name="rawMaterial"
                            />
                            <label className="formulationModalEdit-select-item-yes">Sim</label>
                            <input
                                ref={register}
                                type="radio"
                                value="false"
                                name="rawMaterial"
                            /> Não
                        </div>
                    </div>

                    <div className="formulationModalEdit-type">
                        <label className="label-base">Tipo? </label>
                        <select name="type" ref={register} className="parameter-content-type-cost-select">
                            <option value="NORMAL">Normal</option>
                            <option value="EXTRA">Extra</option>
                        </select>
                    </div>

                    <div className="formulationModalEdit-actions">
                        <button
                            className="btn btn-primary formulationModalEdit-btn-save"
                            onClick={handleSubmit(onSave)}                    >
                            Salvar
                        </button>

                        <button
                            className="btn btn-danger formulationModalEdit-btn-del"
                            onClick={() => setShow(!show)}
                        >
                            Cancelar
                        </button>
                    </div>
                </div>


            </Modal>
        </>
    );
}
export default FormulationItemEditModal;
