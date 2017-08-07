import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Shelter } from './shelter.model';
import { ShelterService } from './shelter.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-shelter',
    templateUrl: './shelter.component.html'
})
export class ShelterComponent implements OnInit, OnDestroy {
shelters: Shelter[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private shelterService: ShelterService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.shelterService.query().subscribe(
            (res: ResponseWrapper) => {
                this.shelters = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInShelters();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Shelter) {
        return item.id;
    }
    registerChangeInShelters() {
        this.eventSubscriber = this.eventManager.subscribe('shelterListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
