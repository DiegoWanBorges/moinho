import Filter from 'core/components/Filter';
import Pagination from 'core/components/Pagination';
import { PalletstatusResponse } from 'core/types/ProductionOrder';
import { makePrivateRequest } from 'core/utils/request';
import { useCallback, useEffect, useState } from 'react';
import { useHistory } from 'react-router';
import { toast } from 'react-toastify';
import ProducedproductstatusCard from '../Card';
import CardLoader from 'core/components/CardLoader';
import './styles.scss';

function ProducedProductStatusList() {
    const [producedProductStatusResponse, setProducedProductStatusResponse] = useState<PalletstatusResponse>();
    const [activePage, setActivePage] = useState(0);
    const [name, setName] = useState('');
    const [linesPerPage, setLinesPerPage] = useState(10);
    const [isLoading, setIsLoading] = useState(false);

    const getProducedProductStatus = useCallback(() => {
        setIsLoading(true)
        const params = {
            page: activePage,
            linesPerPage: linesPerPage,
            name: name,
            orderBy: "id",
            direction: "DESC"
        }
        makePrivateRequest({ url: '/palletstatus', params })
            .then(response => setProducedProductStatusResponse(response.data))
            .finally(()=>setIsLoading(false))
    }, [activePage, name, linesPerPage])

    useEffect(() => {
        getProducedProductStatus();
    }, [getProducedProductStatus])

    const history = useHistory();


    const handCreate = () => {
        history.push("/registrations/palletstatus/new");
    }
    const onRemove = (producedProductStatusId: number) => {
        const confirm = window.confirm("Deseja excluir o status selecionado?");
        if (confirm) {
            makePrivateRequest({
                url: `/palletstatus/${producedProductStatusId}`,
                method: 'DELETE'
            })
                .then(() => {
                    toast.success("Status excluido com sucesso!")
                    history.push('/registrations/palletstatus')
                    getProducedProductStatus();
                })
                .catch(() => {
                    toast.error("Falha ao excluir status!")
                    history.push('/registrations/palletstatus')
                })
        }
    }
    const handleChangeName = (name: string) => {
        setActivePage(0);
        setName(name);
    }
    const handleChangeLinesPerPage = (linesPerPage: number) => {
        setActivePage(0);
        setLinesPerPage(linesPerPage)
    }
    const clearFilters = () => {
        setActivePage(0);
        setName('');
        setLinesPerPage(10);
    }
    return (
        <div className="producedProductStatus-list">
            <div className="producedProductStatus-list-add-filter">
                <button
                    className="btn btn-primary btn-lg producedProductStatus-list-btn-add"
                    onClick={handCreate}
                >
                    ADCIONAR
                </button>
                <Filter
                    name={name}
                    handleChangeName={handleChangeName}
                    linesPerPage={linesPerPage}
                    handleChangeLinesPerPage={handleChangeLinesPerPage}
                    clearFilters={clearFilters}
                    placeholder="Digite o nome do status"
                />
            </div>
            <div className="admin-list-container">
                {isLoading ? <CardLoader /> : (
                    producedProductStatusResponse?.content.map(status => (
                        <ProducedproductstatusCard
                            producedProductStatus={status} key={status.id}
                            onRemove={onRemove}
                        />
                    )))}

                {producedProductStatusResponse &&
                    <Pagination
                        totalPages={producedProductStatusResponse?.totalPages}
                        onChange={page => setActivePage(page)}
                    />
                }
            </div>
        </div>
    );
}
export default ProducedProductStatusList;
