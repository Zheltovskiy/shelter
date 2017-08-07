import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Shelter } from './shelter.model';
import { ShelterPopupService } from './shelter-popup.service';
import { ShelterService } from './shelter.service';

@Component({
    selector: 'jhi-shelter-dialog',
    templateUrl: './shelter-dialog.component.html'
})
export class ShelterDialogComponent implements OnInit {

    shelter: Shelter;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private shelterService: ShelterService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.shelter.id !== undefined) {
            this.subscribeToSaveResponse(
                this.shelterService.update(this.shelter));
        } else {
            this.subscribeToSaveResponse(
                this.shelterService.create(this.shelter));
        }
    }

    private subscribeToSaveResponse(result: Observable<Shelter>) {
        result.subscribe((res: Shelter) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Shelter) {
        this.eventManager.broadcast({ name: 'shelterListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-shelter-popup',
    template: ''
})
export class ShelterPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shelterPopupService: ShelterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.shelterPopupService
                    .open(ShelterDialogComponent, params['id']);
            } else {
                this.modalRef = this.shelterPopupService
                    .open(ShelterDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
