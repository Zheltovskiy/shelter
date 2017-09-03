import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import {Pet} from "../entities/pet/pet.model";
import {PetService} from "../entities/pet/pet.service";
import {Principal} from "../shared/auth/principal.service";
import {ResponseWrapper} from "../shared/model/response-wrapper.model";


@Component({
    selector: 'jhi-home',
    templateUrl: './home-visitor.component.html'
})
export class HomeVisitorComponent implements OnInit {
    pets: Pet[];

    constructor(
        private petService: PetService,
        private alertService: JhiAlertService,
        private dataUtils: JhiDataUtils
    ) {
    }

    loadAll() {
        this.petService.query().subscribe(
            (res: ResponseWrapper) => {
                this.pets = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
    }

    trackId(index: number, item: Pet) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }


    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
