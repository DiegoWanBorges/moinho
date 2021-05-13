import Filter from 'core/components/Filter';
import Pagination from 'core/components/Pagination';
import { FormulationResponse } from 'core/types/Formulation';
import { makePrivateRequest } from 'core/utils/request';
import { useCallback, useEffect, useState } from 'react';
import { useHistory } from 'react-router';
import { toast } from 'react-toastify';
import FormulationCard from '../Card';
import './styles.scss';

function FormulationList() {
    const [formulationsResponse, setFormulationsResponse] = useState<FormulationResponse>();
    const [activePage, setActivePage] = useState(0);
    const [name, setName] = useState('');
  
    const getFormulations = useCallback(() => {
        const params = {
            page: activePage,
            linesPerPage: 3,
            description:name,
            orderBy:"id",
            direction:"DESC"
        }
        makePrivateRequest({ url: '/formulations', params })
            .then(response => setFormulationsResponse(response.data))
            .finally(() => {
    
            })
    }, [activePage,name])

    useEffect(() => {
        getFormulations();
    }, [getFormulations])

    const history = useHistory();


    const handCreate = () => {
        history.push("/formulations/registrations/new");
    }
    const onRemove = (formulationId: number) => {
        const confirm = window.confirm("Deseja excluir a formulação selecionada?");
        if (confirm) {
            makePrivateRequest({
                url: `/formulations/${formulationId}`,
                method: 'DELETE'
            })
                .then(() => {
                    toast.success("Formulação excluida com sucesso!")
                    history.push('/formulations/registrations')
                    getFormulations();
                })
                .catch(() => {
                    toast.error("Falha ao excluir formulação!")
                    history.push('/formulations/registrations')
                })
        }
    }
    const handleChangeName = (name: string) => {
        setActivePage(0);
        setName(name);
    }
    const clearFilters = () => {
        setActivePage(0);
        setName('');
    }
    return (
        <div className="group-list">
           <div className="group-list-add-filter">
               <button
                className="btn btn-primary btn-lg group-list-btn-add"
                onClick={handCreate}
               >
                   ADCIONAR
               </button>
               <Filter
                        name={name}
                        handleChangeName={handleChangeName}
                        clearFilters={clearFilters}
                        placeholder="Digite o nome do grupo"
                />
           </div>
           <div className="admin-list-container">
                {formulationsResponse?.content.map(formulation => (
                    <FormulationCard
                        formulation={formulation} key={formulation.id}
                        onRemove={onRemove}
                    />
                ))}

                {formulationsResponse &&
                    <Pagination
                        totalPages={formulationsResponse?.totalPages}
                        onChange={page => setActivePage(page)}
                    />
                }
            </div>
        </div>
    );
}
export default FormulationList;
