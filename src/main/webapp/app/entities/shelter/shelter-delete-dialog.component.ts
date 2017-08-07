import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Shelter } from './shelter.model';
import { ShelterPopupService } from './shelter-popup.service';
import { ShelterService } from './shelter.service';

@Component({
    selector: 'jhi-shelter-delete-dialog',
    templateUrl: './shelter-delete-dialog.component.html'
})
export class ShelterDeleteDialogComponent {

    shelter: Shelter;

    constructor(
        private shelterService: ShelterService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.shelterService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'shelterListModification',
                content: 'Deleted an shelter'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-shelter-delete-popup',
    template: ''
})
export class ShelterDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private shelterPopupService: ShelterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.shelterPopupService
                .open(ShelterDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
